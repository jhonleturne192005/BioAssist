import {  UseFetchPOSTEvent } from "../../useFetch";
import toast from "react-hot-toast";
import { KEY } from "../../Messages";
import { InterfaceRecurso } from "../../interfaces/InterfaceRecurso";
import { useEffect, useState } from "react";
import styles from '../UtilsComponents/AccessUtils.module.css'
import NavBar from "../UtilsComponents/NavBar";

function Entrenamiento()
{
    const formData = new FormData();
    formData.append('key',String(localStorage.getItem(KEY)));

    const [EarrayRecurso,setRecurso]=useState<InterfaceRecurso[]>([]);
    const [cambiodata,setCambioData]=useState(false);
    
    //const formData = new FormData();
    //formData.append('key',String(localStorage.getItem(KEY)));
    //const arrayRecurso=UseFetchPOST(formData,'reconocimiento/listar',true).data.data as unknown as InterfaceRecurso[];

    useEffect(() => {
        
        const formData = new FormData();
        formData.append('key',String(localStorage.getItem(KEY)));
        const promiseListarDatos=UseFetchPOSTEvent(formData,'reconocimiento/listar');
        promiseListarDatos.then((data)=>{
            console.log(data.data);
            const datarec=data.data.data as InterfaceRecurso[];
            console.log("chamadre");
            console.log(data);
            setRecurso(datarec);
        })
        .catch(err=>toast.error(err.response.data.error));   
    }, [cambiodata]);

    

    const handleClickGuardar=(e:React.FormEvent<HTMLFormElement>)=>
    {
        e.preventDefault();
        const formData = new FormData();
        const promiseGuardarDatos=UseFetchPOSTEvent(formData,'reconocimiento/entrenar');
        promiseGuardarDatos.then(data=>{
            toast.success(data.data.successful); setCambioData(true);
        })
        .catch(err=>toast.error(err.response.data.error));

       /* useEffect(() => {
            const promiseListarDatos=UseFetchPOSTEvent(formData,'reconocimiento/listar');
            promiseListarDatos.then((data)=>{
                console.log(data.data);
                const datarec=data.data as InterfaceRecurso;
                console.log(datarec);
                setRecurso([datarec]);
            })
            .catch(err=>toast.error(err.response.data.error));        
        }, [cambio_data]);
       */

    }


    return(
        <>
            <NavBar/>

            <div className="container mt-5 ">
                <table className="table table-sm">
                    <thead className="text-muted">
                        <tr>
                            <th scope="col" className="small">Nombre modelo</th>
                        </tr>
                    </thead>
                    <tbody>
                    {EarrayRecurso.map((elemento:InterfaceRecurso)=> 
                    
                    <tr key={elemento.idrecursos}>
                        <td className="small">{elemento.nombremodeloxml}</td>
                    </tr>
                    )}
                    
                    </tbody>
                </table>
            </div>

            <div className={styles.container_registro}>
                <form method="POST" name="form" className={styles.formWi} onSubmit={handleClickGuardar} >
                    <div className="d-grid gap-2">
                        <button type="submit" className="btn btn-primary btn-lg">Entrenar</button>
                    </div>
                </form>
            </div>
        </>
    );
}


export default Entrenamiento;