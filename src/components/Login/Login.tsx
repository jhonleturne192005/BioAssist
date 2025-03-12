import toast from "react-hot-toast";
import { ADMIN, ERROR_CAMPOS_VACIOS, KEY } from "../../Messages";
import { UseFetchPOSTEvent } from "../../useFetch";
import { InterfaceLogin } from "../../interfaces/InterfaceLogin";
import { useNavigate } from "react-router-dom";
import styles from "../Curso/Curso.module.css"

function Login()
{
    const navigate=useNavigate();

    navigator.geolocation.getCurrentPosition((position)=>{
        console.log(position.coords.latitude+" - "+position.coords.longitude);
    },
    (error)=>{
        console.log(error.message);
    },{
        enableHighAccuracy:true
    });


    const handleClickLogin=(e:React.FormEvent<HTMLFormElement>)=>
    {
        e.preventDefault();
        console.log(e);
        const correo:string=(e.currentTarget.elements.namedItem('correo') as HTMLInputElement).value;
        const contrasenia:string=(e.currentTarget.elements.namedItem('contrasenia') as HTMLInputElement).value;

        if(correo!="" && contrasenia!="")
        {
            const formData = new FormData();
            formData.append('correo',correo.trim());
            formData.append('contrasenia',contrasenia.trim());

            navigator.geolocation.getCurrentPosition((position)=>{
                console.log(position.coords.latitude+" - "+position.coords.longitude);
                    
                formData.append('longitud',position.coords.longitude.toString());
                formData.append('latitud',position.coords.latitude.toString());

                console.log("Este es el curso= "+contrasenia);
                const promiseGuardarDatos=UseFetchPOSTEvent(formData,'persona/login');
                promiseGuardarDatos.then(data=>
                {
                    const dataLogin:InterfaceLogin=data.data;
    
                    if(!dataLogin.estado){
                        toast.error(dataLogin.successful);
                        return;
                    } 
                    alert(String(dataLogin.admin))
                    localStorage.setItem(ADMIN,String(dataLogin.admin));
                    localStorage.setItem(KEY,dataLogin.correo);
                    console.log(data);
                    toast.success(data.data.successful)
                    navigate("/home");
                })
                .catch(err=>toast.error(err.response.data.error));

            },
            (error)=>{
                console.log(error.message);
            },{
                enableHighAccuracy:true
            });
        }
        else{
            toast.error(ERROR_CAMPOS_VACIOS);
        }
    }


    return (
        <div className={styles.container_registro}>
            <form method="POST" name="form" className={styles.formWi} onSubmit={handleClickLogin} >
                <div className="mb-3 controles_input">
                    <label htmlFor="correo" className="form-label"><b>Correo</b></label>
                    <input type="email" className="form-control" id="correo" maxLength={100} required name="correo" />
                </div>
                <div className="mb-3 controles_input">
                    <label htmlFor="contrasenia" className="form-label"><b>Contraseña</b></label>
                    <input type="password" className="form-control" id="contrasenia" required name="contrasenia" />
                </div>
                <button type="submit" className="btn btn-primary">Iniciar Sesion</button>
            </form>
        </div>
    );
}


export default Login;