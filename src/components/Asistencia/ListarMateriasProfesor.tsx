import { KEY } from "../../Messages";
import { UseFetchPOST } from "../../useFetch";
import SelectDiv from "../UtilsComponents/SelectDiv";
import { InterfaceMateriaPorPersona } from "../../interfaces/InterfaceMateriaPorPersona";
import NavBar from "../UtilsComponents/NavBar";

function ListarMateriasProfesor()
{
    const formData = new FormData();
    formData.append('correopersona',String(localStorage.getItem(KEY)));

    const arrayCurso=UseFetchPOST(formData,'materiaporpersona/listarporpersona',true).data.data as unknown as InterfaceMateriaPorPersona[];

    return(
        <>
            <NavBar/>
            <div className="container mt-5 ">
                <table className="table table-sm">
                    <thead className="text-muted">
                        <tr>
                            <th scope="col" className="small">Curso</th>
                            <th scope="col" className="small">Materia</th>
                            <th scope="col" className="small">Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    {arrayCurso.map((elemento:InterfaceMateriaPorPersona)=> 
                    
                    <tr key={elemento.idmateriaporpersona}>
                        <td className="small">{elemento.idmateria.idcurso.curso}</td>
                        <td className="small">{elemento.idmateria.materia}</td>
                        <td>
                            <SelectDiv 
                                nameops={["Matriculados"]}
                                links={[`/matriculados/${elemento.idmateriaporpersona}`]}
                                eventClick={[(e)=>e]}
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


export default ListarMateriasProfesor;