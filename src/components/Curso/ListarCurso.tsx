import toast from "react-hot-toast";
import { InterfaceCurso } from "../../interfaces/InterfaceCurso";
import { KEY } from "../../Messages";
import { UseFetchPOST, UseFetchPOSTEvent } from "../../useFetch";
import ButtonEstado from "../UtilsComponents/ButtonEstado";
import SelectDiv from "../UtilsComponents/SelectDiv";
import NavBarAdmin from "../UtilsComponents/NavBarAdmin";

function ListarCurso()
{

    const formData = new FormData();
    formData.append('key',String(localStorage.getItem(KEY)));

    const arrayCurso=UseFetchPOST(formData,'curso/listar',true).data.data as unknown as InterfaceCurso[];

    function HandledesactivarActivar(e: React.MouseEvent<HTMLAnchorElement, MouseEvent>,idcurso:number,type:boolean)
    {
        e;
        formData.append('idcurso',String(idcurso));
        const dataresponse=UseFetchPOSTEvent(formData,type?'curso/desactivar':'curso/activar');
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
            <div className="container mt-5 ">
                <table className="table table-sm">
                    <thead className="text-muted">
                        <tr>
                            <th scope="col" className="small">Curso</th>
                            <th scope="col" className="small">Estado</th>
                            <th scope="col" className="small">Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    {arrayCurso.map((elemento:InterfaceCurso)=> 
                    
                    <tr key={elemento.idcurso}>
                        <td className="small">{elemento.curso}</td>
            
                        <td className="small"><ButtonEstado elementoValue={elemento.estadocurso} /></td>
                        <td>
                            <SelectDiv 
                                nameops={["Editar","Desactivar","Activar"]}
                                links={[`/actualizarcurso/${elemento.idcurso}`,"#","#"]}
                                eventClick={[(e)=>e,
                                            (e)=>HandledesactivarActivar(e,elemento.idcurso,true),
                                            (e)=>HandledesactivarActivar(e,elemento.idcurso,false),
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


export default ListarCurso;