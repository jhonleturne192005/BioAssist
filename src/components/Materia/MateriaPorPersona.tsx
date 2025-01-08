import { useParams } from "react-router-dom";
import { ERROR_CAMPOS_VACIOS, KEY } from "../../Messages";
import { UseFetchPOST, UseFetchPOSTEvent } from "../../useFetch";
import { InterfaceMateria } from "../../interfaces/InterfaceMateria";
import { InterfacePersona } from "../../interfaces/InterfacePersona";
import { useEffect, useState } from "react";
import { InterfaceMateriaPorPersona } from "../../interfaces/InterfaceMateriaPorPersona";
import toast from "react-hot-toast";
import styles from "../Curso/Curso.module.css"
import NavBarAdmin from "../UtilsComponents/NavBarAdmin";


function MateriaPorPersona()
{

    const { idmateriaporpersona } = useParams();

    const formData = new FormData();
    formData.append('key',String(localStorage.getItem(KEY)),);
    formData.append('idmateriaporpersona',String(idmateriaporpersona));
    
    const arrayMaterias=UseFetchPOST(formData,'materia/listar',true).data.data as unknown as InterfaceMateria[];
    const arrayPersonasProfesores=UseFetchPOST(formData,'persona/listarprofesores',true).data.data as unknown as InterfacePersona[];
    const arrayMateriasPorPersonaID=UseFetchPOST(formData,'materiaporpersona/listarmateriaporpersonaid',true).data.data as unknown as InterfaceMateriaPorPersona[];
    console.log(arrayMateriasPorPersonaID);
    const handleClickGuardar=(e:React.FormEvent<HTMLFormElement>)=>
    {
        e.preventDefault();
        console.log(e);
        const materia:string=(e.currentTarget.elements.namedItem('materia') as HTMLInputElement).value;
        const profesor:string=(e.currentTarget.elements.namedItem('profesor') as HTMLInputElement).value;
      
        console.log(profesor+" "+materia);

        if(materia!="" && profesor!="")
        {
            const formData = new FormData();
            formData.append('idmateria',materia.trim());
            formData.append('idpersona',profesor.trim());
            console.log("Este es el tipo persona= "+profesor);


            if(idmateriaporpersona)
                formData.append('idmateriaporpersona',idmateriaporpersona);

            const promiseGuardarDatos=UseFetchPOSTEvent(formData,idmateriaporpersona!=undefined?'materiaporpersona/actualizar':'materiaporpersona/crear');
            promiseGuardarDatos.then(data=>toast.success(data.data.successful))
            .catch(err=>toast.error(err.response.data.error));
        }
        else{
            toast.error(ERROR_CAMPOS_VACIOS);
        }
    }


    const [EselectMateria,setSelectMateria]=useState("");
    const [EselectProfesor,setSelectProfesor]=useState("");


    const handleChangeSelectMateria= (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectMateria(e.target.value);
    };

    const handleChangeSelectProfesor=(e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectProfesor(e.target.value);
    };

    
    useEffect(() => {
        setSelectMateria(String(arrayMateriasPorPersonaID.length>0?arrayMateriasPorPersonaID[0].idmateria.idmateria:0));
        setSelectProfesor(String(arrayMateriasPorPersonaID.length?arrayMateriasPorPersonaID[0].idpersona.idpersona:0));
    }, [arrayMateriasPorPersonaID]);
    

    return(
        <>
            <NavBarAdmin/>
            <div className={styles.container_registro}>
                <form method="POST" name="form" className={styles.formWi} onSubmit={handleClickGuardar} >
                    <div className="mb-3">
                        <label htmlFor="materia" className="form-label">Materia</label>
                        <select value={EselectMateria} onChange={handleChangeSelectMateria} id="materia" className="form-select" name="materia">
                            {arrayMaterias.map((elemento:InterfaceMateria)=> <option key={elemento.idmateria} value={elemento.idmateria}>{elemento.materia}</option>)}
                        </select>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="profesor" className="form-label">Profesor</label>
                        <select value={EselectProfesor} onChange={handleChangeSelectProfesor} id="profesor" className="form-select" name="profesor">
                            {arrayPersonasProfesores.map((elemento:InterfacePersona)=> <option key={elemento.idpersona} value={elemento.idpersona}>{elemento.nombres} {elemento.apellidos}</option>)}
                        </select>
                    </div>
                    <button type="submit" className="btn btn-primary">Guardar</button>
                </form>
            </div>
        </>
    );
}

export default MateriaPorPersona;