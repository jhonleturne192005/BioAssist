import { UseFetchPOST } from "../../useFetch";
import NavBar from "../UtilsComponents/NavBar";
import { useParams } from "react-router-dom";
import { InterfaceAsistenciaMatriculados } from "../../interfaces/InterfaceAsistenciaMatriculados";
import ButtonEstadoAsistencia from "../UtilsComponents/ButtonEstadoAsistencia";

function ListarMatriculados()
{
    const { idmateriasporpersona } = useParams();

    const formData = new FormData();
    formData.append('idmateriaporpersona',String(idmateriasporpersona));

    const arrayCurso=UseFetchPOST(formData,'materiaporpersona/listarasistenciamatriculados',true).data.data as unknown as InterfaceAsistenciaMatriculados[];

    return(
        <>
            <NavBar/>
            <div className="container mt-5 ">
                <table className="table table-sm">
                    <thead className="text-muted">
                        <tr>
                            <th scope="col" className="small">Nombres</th>
                            <th scope="col" className="small">Apellidos</th>
                            <th scope="col" className="small">Correo</th>
                            <th scope="col" className="small">Telefono</th>
                            <th scope="col" className="small">Etiqueta</th>
                            <th scope="col" className="small">Genero</th>
                            <th scope="col" className="small text-center align-middle">Estado Asistencia</th>
                        </tr>
                    </thead>
                    <tbody>
                    {arrayCurso.map((elemento:InterfaceAsistenciaMatriculados)=> 
                    
                    <tr key={elemento.idmateriasporpersona}>
                        <td className="small">{elemento.nombres}</td>
                        <td className="small">{elemento.apellidos}</td>
                        <td className="small">{elemento.correo}</td>
                        <td className="small">{elemento.numero_telefono}</td>
                        <td className="small">{elemento.etiqueta_reconocer}</td>
                        <td className="small">{elemento.genero}</td>
                        <td className="small text-center align-middle"><ButtonEstadoAsistencia elementoValue={elemento.asistencia} /></td>
                    </tr>
                    )}
                    
                    </tbody>
                </table>
            
            </div>
        </>
       
    
    );

}


export default ListarMatriculados;