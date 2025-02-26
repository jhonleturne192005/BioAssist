package com.example.movilestudiantes

import android.Manifest
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movilestudiantes.ActivityCamera.Companion
import com.example.movilestudiantes.databinding.ActivityCameraBinding
import com.example.movilestudiantes.databinding.ActivityCameraVideoBinding
import com.example.movilestudiantes.utils.ApiService
import com.example.movilestudiantes.utils.AsignarRecursoRequest
import com.example.movilestudiantes.utils.DataStatic
import com.example.movilestudiantes.utils.GlobalDataUser
import com.example.movilestudiantes.utils.RetrofitService
import com.example.movilestudiantes.utils.ToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URI
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Timer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.schedule

class ActivityCameraVideo : AppCompatActivity()
{

    private lateinit var binding: ActivityCameraVideoBinding;
    private var videoCapture: VideoCapture<Recorder>? = null;
    private var recording: Recording? = null;
    private lateinit var cameraExecutor: ExecutorService;
    private lateinit var UriVideo:Uri;
    private var base64video="";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityCameraVideoBinding.inflate(layoutInflater);
        setContentView(binding.root);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init();
    }

    private fun init()
    {
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
                this, ActivityCameraVideo.REQUIRED_PERMISSIONS, ActivityCameraVideo.REQUEST_CODE_PERMISSIONS
            )
        }

        cameraExecutor = Executors.newSingleThreadExecutor();

        binding.btngrabarvideo.setOnClickListener{captureVideo()}

        binding.btnrepetirvideo.setOnClickListener{
            binding.videopreview.visibility= View.VISIBLE;
            binding.videocamerapreview.visibility= View.GONE;
            baseContext.contentResolver.delete(UriVideo,null,null);
        }

        binding.btnenviarvideo.setOnClickListener{

            CoroutineScope(Dispatchers.IO).launch {

                //Log.i("videobase64dd",base64video);



                var objDataResponseReconocimiento= RetrofitService().getRetrofit().create(
                    ApiService::class.java).asignarrecurso(AsignarRecursoRequest(base64video));

                if(objDataResponseReconocimiento.isSuccessful)
                {

                    try
                    {
                        val asignacionrecursosreconocimiento=objDataResponseReconocimiento.body()!!;

                        val objDataActualizarEtiqueta= RetrofitService().getRetrofit().create(
                            ApiService::class.java).ActualizarEtiquetaReconocimientoPersona(GlobalDataUser.userId!!,asignacionrecursosreconocimiento.ooid);

                        if(objDataActualizarEtiqueta.isSuccessful)
                        {
                            withContext(Dispatchers.Main)
                            {
                                ToastMessage().toast(
                                    this@ActivityCameraVideo,
                                    objDataActualizarEtiqueta.body()!!.successful
                                )
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
                val recorder = Recorder.Builder()
                    .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
                    .build()
                videoCapture = VideoCapture.withOutput(recorder)


                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                //val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                try {
                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll()

                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview,videoCapture)

                } catch(exc: Exception) {
                    Log.e("ERROR_CAMARA", "Use case binding failed", exc)
                }
            }, ContextCompat.getMainExecutor(this))


        } catch(exc: Exception) {
            Log.e("ERROR_CAMARA", "Use case binding failed", exc)
        }

    }


    private fun captureVideo()
    {
        val videoCapture = this.videoCapture ?: return


        val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/CameraX-Video")
            }
        }

        val mediaStoreOutputOptions = MediaStoreOutputOptions
            .Builder(contentResolver, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            .setContentValues(contentValues)
            .build()

        recording = videoCapture.output
            .prepareRecording(this, mediaStoreOutputOptions)
            .start(ContextCompat.getMainExecutor(this)) { recordEvent ->
                when(recordEvent)
                {
                    is VideoRecordEvent.Start -> {
                        desactivarBotones(false);
                    }
                    is VideoRecordEvent.Finalize -> {

                        try{
                            if (!recordEvent.hasError())
                            {
                                val inputStream = baseContext.contentResolver.openInputStream(Uri.parse(recordEvent.outputResults.outputUri.toString()))
                                val base64img= Base64.encodeToString(inputStream!!.readBytes(), Base64.DEFAULT);

                                base64video=base64img;

                                Log.i("videobase64",base64img);
                                UriVideo=Uri.parse(recordEvent.outputResults.outputUri.toString())
                                //baseContext.contentResolver.delete(Uri.parse(recordEvent.outputResults.outputUri.toString()),null,null);
                                //val byteimg=Base64.decode(base64img,Base64.DEFAULT)
                                //val bitmap = BitmapFactory.decodeByteArray(byteimg,0,byteimg.size)

                                binding.videopreview.visibility= View.GONE;
                                binding.videocamerapreview.visibility= View.VISIBLE;
                                binding.videocamerapreview.setVideoURI(Uri.parse(recordEvent.outputResults.outputUri.toString()))

                                binding.videocamerapreview.setOnPreparedListener { mediaPlayer ->

                                    //ancho - alto  del video
                                    val videoWidth = mediaPlayer.videoWidth
                                    val videoHeight = mediaPlayer.videoHeight

                                    Log.i("anchoalto",videoWidth.toString());
                                    Log.i("anchoalto",videoHeight.toString());

                                    val videoProportion = videoWidth.toFloat() / videoHeight

                                    mediaPlayer.isLooping = true
                                    binding.videocamerapreview.start()
                                }

                            }
                            else {
                                recording?.close()
                                recording = null
                                Log.e(TAG, "Video capture ends with error: " +
                                        "${recordEvent.error}")
                            }
                        }
                        catch(exc: Exception) {
                            Log.e("ERROR_CAMARA",  exc.message.toString())
                        }


                        desactivarBotones(true);
                    }
                }
            }

        Handler(Looper.getMainLooper()).postDelayed({
            val curRecording = recording;
            if (curRecording != null) {
                curRecording.stop()
                recording = null
            }
        }, 3000)
    }


    private fun desactivarBotones(valor:Boolean)
    {
        binding.btngrabarvideo.isEnabled=valor;
        binding.btnenviarvideo.isEnabled=valor;
        binding.btnrepetirvideo.isEnabled=valor;
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == ActivityCameraVideo.REQUEST_CODE_PERMISSIONS) {
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