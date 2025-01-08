import toast from "react-hot-toast";
import { InterfaceMateria } from "../../interfaces/InterfaceMateria";
import { KEY } from "../../Messages";
import { UseFetchPOST, UseFetchPOSTEvent } from "../../useFetch";
import ButtonEstado from "../UtilsComponents/ButtonEstado";
import SelectDiv from "../UtilsComponents/SelectDiv";
import NavBarAdmin from "../UtilsComponents/NavBarAdmin";


function ListarMateria()
{
    const formData = new FormData();
    formData.append('key',String(localStorage.getItem(KEY)));

    const arrayMateria=UseFetchPOST(formData,'materia/listar',true).data.data as unknown as InterfaceMateria[];

    function HandledesactivarActivar(e: React.MouseEvent<HTMLAnchorElement, MouseEvent>,idmateria:number,type:boolean)
    {
        e;
        formData.append('idmateria',String(idmateria));
        const dataresponse=UseFetchPOSTEvent(formData,type?'materia/desactivar':'materia/activar');
        dataresponse.then(data=>{
            console.log(data);
            toast.success(data.data.successful);
        })
        .catch(err=> {
            console.log(err.response.data.error);
            toast.error(err.response.data.error);
        });
    }


    return(
        <>
            <NavBarAdmin/>
            <div className="container mt-5">
                <table className="table table-sm">
                    <thead className="text-muted">
                        <tr>
                            <th scope="col" className="small">Materia</th>
                            <th scope="col" className="small">Curso</th>
                            <th scope="col" className="small">Estado</th>
                            <th scope="col" className="small">Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    {arrayMateria.map((elemento:InterfaceMateria)=> 
                    
                    <tr key={elemento.idmateria}>
                        <td className="small">{elemento.materia}</td>
                        <td className="small">{elemento.idcurso.curso}</td>
                        <td className="small"><ButtonEstado elementoValue={elemento.estadomateria} /></td>
                        <td>
                            <SelectDiv 
                                nameops={["Editar","Desactivar","Activar"]}
                                links={[`/actualizarmateria/${elemento.idmateria}`,"#","#"]}
                                eventClick={[(e)=>e,
                                            (e)=>HandledesactivarActivar(e,elemento.idmateria,true),
                                            (e)=>HandledesactivarActivar(e,elemento.idmateria,false),
                                            ]}
                                >
                            </SelectDiv>
                        </td>
                    </tr>
                    )}
                    
                    </tbody>
                </table>
            
            </div>
        </>
    );


}

export default ListarMateria;