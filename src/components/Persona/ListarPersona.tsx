import toast from "react-hot-toast";
import { InterfacePersona } from "../../interfaces/InterfacePersona";
import { KEY } from "../../Messages";
import { UseFetchPOST, UseFetchPOSTEvent } from "../../useFetch";
import ButtonEstado from "../UtilsComponents/ButtonEstado";
import SelectDiv from "../UtilsComponents/SelectDiv";
import NavBarAdmin from "../UtilsComponents/NavBarAdmin";

function ListarPersona()
{

    const formData = new FormData();
    formData.append('key',String(localStorage.getItem(KEY)),);

    const arrayPersona=UseFetchPOST(formData,'persona/listar',true).data.data as unknown as InterfacePersona[];

    function HandledesactivarActivar(e: React.MouseEvent<HTMLAnchorElement, MouseEvent>,idpersona:number,type:number)
    {
        e;
        formData.append('idpersona',String(idpersona));
        const dataresponse=UseFetchPOSTEvent(formData,type==1?'persona/desactivar':
                                                        type==2?'persona/activar':
                                                        type==3?'persona/activaredicion':
                                                        "persona/desactivaredicion");
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
                            <th scope="col" className="small">Nombres</th>
                            <th scope="col" className="small">Apellidos</th>
                            <th scope="col" className="small">Correo</th>
                            <th scope="col" className="small">Telefono</th>
                            <th scope="col" className="small">Edicion</th>
                            <th scope="col" className="small">Estado</th>
                            <th scope="col" className="small">Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    {arrayPersona.map((elemento:InterfacePersona)=> 
                    
                    <tr key={elemento.idpersona}>
                        <td className="small">{elemento.nombres}</td>
                        <td className="small">{elemento.apellidos}</td>
                        <td className="small">{elemento.correo}</td>
                        <td className="small">{elemento.numero_telefono}</td>
                        <td className="small"><ButtonEstado elementoValue={elemento.estadopersonaedicion} /></td>        
                        <td className="small"><ButtonEstado elementoValue={elemento.estadopersona} /></td>
                        <td>
                            <SelectDiv 
                                nameops={["Editar","Desactivar","Activar","Activar edición","Desactivar edición"]}
                                links={[`/actualizarpersona/${elemento.idpersona}`,"#","#","#","#"]}
                                eventClick={[(e)=>e,
                                            (e)=>HandledesactivarActivar(e,elemento.idpersona,1),
                                            (e)=>HandledesactivarActivar(e,elemento.idpersona,2),
                                            (e)=>HandledesactivarActivar(e,elemento.idpersona,3),
                                            (e)=>HandledesactivarActivar(e,elemento.idpersona,4),
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

export default ListarPersona;