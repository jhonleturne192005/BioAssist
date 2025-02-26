package com.example.movilestudiantes

import android.Manifest
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movilestudiantes.ActivityAsistencia.Companion.KEYIDHORARIO
import com.example.movilestudiantes.databinding.ActivityCameraBinding
import com.example.movilestudiantes.utils.ApiService
import com.example.movilestudiantes.utils.AsignarRecursoRequest
import com.example.movilestudiantes.utils.DataStatic
import com.example.movilestudiantes.utils.GlobalDataUser
import com.example.movilestudiantes.utils.RetrofitService
import com.example.movilestudiantes.utils.ToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ActivityCamera : AppCompatActivity() {

    //https://developer.android.com/codelabs/camerax-getting-started?hl=es-419#1
    //https://www.youtube.com/watch?v=euvjgNxxhEo


    private lateinit var binding: ActivityCameraBinding;
    //private var videoCapture: VideoCapture<Recorder>? = null;
    //private var recording: Recording? = null;
    private var imageCapture: ImageCapture? = null

    private lateinit var cameraExecutor: ExecutorService;
    private var idhorario=0;
    private var base64imagen="";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityCameraBinding.inflate(layoutInflater);
        setContentView(binding.root);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init();
    }


    fun init()
    {
        idhorario= getIntent().getIntExtra(KEYIDHORARIO,0);

        if (allPermissionsGranted()) {
            startCamera()
            Toast.makeText(this,
                "siii.",
                Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,
                "nooo.",
                Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        binding.btntomarfoto.setOnClickListener{
            captureImage();
        }

        binding.btnrepetirfoto.setOnClickListener{
            binding.imagepreview.visibility=View.GONE;
            binding.videopreview.visibility=View.VISIBLE;
        }

        binding.btnenviarfoto.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {

                var objDataResponseReconocimiento= RetrofitService().getRetrofit().create(
                    ApiService::class.java).reconocer(AsignarRecursoRequest(base64imagen));
                if(objDataResponseReconocimiento.isSuccessful)
                {
                    try
                    {
                        val reconocimiento=objDataResponseReconocimiento.body()!!;

                        if(reconocimiento.data.reconocio)
                        {
                            var objDataResponseMatricula= RetrofitService().getRetrofit().create(ApiService::class.java).asistencia(
                                GlobalDataUser.userId!!,idhorario,"");
                            if(objDataResponseMatricula.isSuccessful)
                            {
                                val toast= ToastMessage()

                                try {
                                    withContext(Dispatchers.Main)
                                    {
                                        toast.toast(
                                            this@ActivityCamera,
                                            objDataResponseMatricula.body()!!.successful
                                        );

                                        val intent = Intent(this@ActivityCamera, ActivityAsistencia::class.java)
                                        startActivity(intent)

                                    }
                                }
                                catch (e: Exception)
                                {
                                    withContext(Dispatchers.Main)
                                    {
                                        toast.toast(this@ActivityCamera, toast.informacionErrorMessageGuardar);
                                    }
                                }
                            }

                        }else
                        {
                            withContext(Dispatchers.Main)
                            {
                                val toast= ToastMessage()
                                toast.toast(this@ActivityCamera, "Error al reconocer vuelva a intentarlo");
                            }
                        }
                    }
                    catch (e: Exception)
                    {
                        Log.i(DataStatic.LogError, e.message.toString())
                    }
                }
            }

        }

        cameraExecutor = Executors.newSingleThreadExecutor();
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun startCamera()
    {
        try {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

            cameraProviderFuture.addListener({

                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(binding.videopreview.surfaceProvider)
                    }
                imageCapture = ImageCapture.Builder().build()

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll()

                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview,imageCapture)

                } catch(exc: Exception) {
                    Log.e("ERROR_CAMARA", "Use case binding failed", exc)
                }
            }, ContextCompat.getMainExecutor(this))


        } catch(exc: Exception) {
            Log.e("ERROR_CAMARA", "Use case binding failed", exc)
        }

    }


    private fun captureImage()
    {
        val imageCapture = imageCapture ?: return
        val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()


        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {

                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)

                    Toast.makeText(baseContext,
                        "Error al capturar imagen. Vuelva a intentarlo",
                        Toast.LENGTH_SHORT).show()

                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults){
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)

                    try
                    {
                        //val file= output.savedUri!!.toFile()

                        val inputStream = baseContext.contentResolver.openInputStream(Uri.parse(output.savedUri.toString()))

                        val base64img=Base64.encodeToString(inputStream!!.readBytes(),Base64.DEFAULT);
                        Log.i("imagenbase64",base64img);
                        base64imagen=base64img
                        baseContext.contentResolver.delete(Uri.parse(output.savedUri.toString()),null,null);


                        val byteimg=Base64.decode(base64img,Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(byteimg,0,byteimg.size)

                        binding.videopreview.visibility= View.GONE;
                        binding.imagepreview.visibility=View.VISIBLE;
                        binding.imagepreview.setImageBitmap(bitmap)

                    } catch(exc: Exception) {
                        Log.e("ERROR_CAMARA",  exc.message.toString())
                    }

                }
            }
        )
    }






    //permisos
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }



}