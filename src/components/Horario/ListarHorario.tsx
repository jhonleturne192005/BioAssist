import { InterfaceHorario } from "../../interfaces/InterfaceHorario";
import { KEY } from "../../Messages";
import { UseFetchPOST } from "../../useFetch";
import SelectDiv from "../UtilsComponents/SelectDiv";

function ListarHorario()
{
    const formData = new FormData();
    formData.append('key',String(localStorage.getItem(KEY)));

    const arrayHorario=UseFetchPOST(formData,'horario/listar',false).data.data as unknown as InterfaceHorario[];

    return(
        <div className="container mt-5">
            <table className="table table-sm">
                <thead className="text-muted">
                    <tr>
                        <th scope="col" className="small">Dia Semana</th>
                        <th scope="col" className="small">Curso</th>
                        <th scope="col" className="small">Persona</th>
                        <th scope="col" className="small">Hora Inicio</th>
                        <th scope="col" className="small">Hora Fin</th>
                        <th scope="col" className="small">Opciones</th>
                    </tr>
                </thead>
                <tbody>
                {arrayHorario.map((elemento:InterfaceHorario)=> 
                
                <tr key={elemento.idhorario}>
                    <td className="small">{elemento.iddia.dia}</td>
                    <td className="small">{elemento.idmateriaporpersona.idmateria.idcurso.curso}</td>
                    <td className="small">{elemento.idmateriaporpersona.idpersona.nombres} {elemento.idmateriaporpersona.idpersona.apellidos}</td>
                    <td className="small">{elemento.hora_inicio}:{elemento.minuto_inicio}</td>
                    <td className="small">{elemento.hora_fin}:{elemento.minuto_fin}</td>
                    <td>
                        <SelectDiv 
                            nameops={["Editar"]}
                            links={[`/actualizarhorario/${elemento.idhorario}`,"#","#"]}
                            eventClick={[(e)=>e]}
                            >
                        </SelectDiv>
                    </td>
                </tr>
                )}
                
                </tbody>
            </table>
        
        </div>
    );
}

export default ListarHorario;