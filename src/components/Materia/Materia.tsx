import { useParams } from "react-router-dom";
import { ERROR_CAMPOS_VACIOS, KEY } from "../../Messages";
import { UseFetchPOST, UseFetchPOSTEvent } from "../../useFetch";
import { InterfaceCurso } from "../../interfaces/InterfaceCurso";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import { InterfaceMateria } from "../../interfaces/InterfaceMateria";
import NavBarAdmin from "../UtilsComponents/NavBarAdmin";
import styles from "../Curso/Curso.module.css"


function Materia()
{

    const { idmateria } = useParams();

    const formData = new FormData();
    formData.append('key',String(localStorage.getItem(KEY)),);
    formData.append('idmateria',String(idmateria));
    
    const arrayCurso=UseFetchPOST(formData,'curso/listar',true).data.data as unknown as InterfaceCurso[];
    const arrayMateria=UseFetchPOST(formData,'materia/obtenermateriaid',true).data.data as unknown as InterfaceMateria[];


    const handleClickGuardar=(e:React.FormEvent<HTMLFormElement>)=>
    {
        e.preventDefault();
        console.log(e);
        const materia:string=(e.currentTarget.elements.namedItem('materia') as HTMLInputElement).value;
        const curso:string=(e.currentTarget.elements.namedItem('curso') as HTMLInputElement).value;

        console.log(curso);

        if(curso!="")
        {
            const formData = new FormData();
            formData.append('materia',materia.trim());
            formData.append('idcurso',curso.trim());
            console.log("Este es el curso= "+curso);


            if(idmateria)
                formData.append('idmateria',idmateria);

            const promiseGuardarDatos=UseFetchPOSTEvent(formData,idmateria!=undefined?'materia/actualizar':'materia/crear');
            promiseGuardarDatos.then(data=>toast.success(data.data.successful))
            .catch(err=>toast.error(err.response.data.error));
        }
        else{
            toast.error(ERROR_CAMPOS_VACIOS);
        }
    }

    

    const [EselectCurso,setSelectCurso]=useState("");

    const handleChangeSelectCurso = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectCurso(e.target.value);
    };

 

    useEffect(() => {
        setSelectCurso(String(arrayMateria.length>0?arrayMateria[0].idcurso.idcurso:0));
    }, [arrayMateria]);


    return(
        <>
            <NavBarAdmin/>
            <div className={styles.container_registro}>
                <form method="POST" name="form" className={styles.formWi} onSubmit={handleClickGuardar} >
                    <div className="mb-3 controles_input">
                        <label htmlFor="materia" className="form-label"><b>Materia</b></label>
                        <input type="text" defaultValue={arrayMateria.length>0?arrayMateria[0].materia:""} className="form-control" id="materia" maxLength={100} required name="materia" />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="curso" className="form-label">Curso</label>
                        <select value={EselectCurso} onChange={handleChangeSelectCurso} id="curso" className="form-select" name="curso">
                            {arrayCurso.map((elemento:InterfaceCurso)=> <option key={elemento.idcurso} value={elemento.idcurso}>{elemento.curso}</option>)}
                        </select>
                    </div>
                    <button type="submit" className="btn btn-primary">Guardar</button>
                </form>
            </div>
        </>
    );
}

export default Materia;