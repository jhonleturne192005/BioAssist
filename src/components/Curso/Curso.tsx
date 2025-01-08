import toast from "react-hot-toast";
import { ERROR_CAMPOS_VACIOS, KEY } from "../../Messages";
import { UseFetchPOST, UseFetchPOSTEvent } from "../../useFetch";
import { useParams } from "react-router-dom";
import { InterfaceCurso } from "../../interfaces/InterfaceCurso";
import styles from "../Curso/Curso.module.css"

function Curso()
{

    const { idcurso } = useParams();

    const formData = new FormData();
    formData.append('key',String(localStorage.getItem(KEY)),);
    formData.append('idcurso',String(idcurso));

    const arrayCurso=UseFetchPOST(formData,'curso/obtenercursoid',false).data.data as InterfaceCurso[];
    console.log(arrayCurso);

    const handleClickGuardar=(e:React.FormEvent<HTMLFormElement>)=>
    {
        e.preventDefault();
        console.log(e);
        const curso:string=(e.currentTarget.elements.namedItem('curso') as HTMLInputElement).value;
        console.log(curso);

        if(curso!="")
        {
            const formData = new FormData();
            formData.append('curso',curso.trim());

            if(idcurso)
                formData.append('idcurso',idcurso);

            const promiseGuardarDatos=UseFetchPOSTEvent(formData,idcurso!=undefined?'curso/actualizar':'curso/crear');
            promiseGuardarDatos.then(data=>toast.success(data.data.successful))
            .catch(err=>toast.error(err.response.data.error));
        }
        else{
            toast.error(ERROR_CAMPOS_VACIOS);
        }
    }
    
    return(
        <div className={styles.container_registro}>
            <form method="POST" name="form" className={styles.formWi} onSubmit={handleClickGuardar} >
                <div className="mb-3 controles_input">
                    <label htmlFor="curso" className="form-label"><b>Curso</b></label>
                    <input type="text" defaultValue={arrayCurso.length>0?arrayCurso[0].curso:''} className="form-control" id="curso" maxLength={100} required name="curso" />
                </div>
                <button type="submit" className="btn btn-primary">Guardar</button>
            </form>
        </div>
    );
}


export default Curso;