import { useState } from "react";
import { InterfaceMateriaPorPersona } from "../../interfaces/InterfaceMateriaPorPersona";
import { KEY } from "../../Messages";
import { UseFetchPOST } from "../../useFetch";
import SelectDiv from "../UtilsComponents/SelectDiv";
import { InterfaceMateria } from "../../interfaces/InterfaceMateria";
import { InterfacePersona } from "../../interfaces/InterfacePersona";
import NavBarAdmin from "../UtilsComponents/NavBarAdmin";
import { useNavigate } from "react-router-dom";


function ListarMateriaPorPersona()
{
    const navigate=useNavigate();
    const formData = new FormData();
    formData.append('key',String(localStorage.getItem(KEY)));

    const arrayMateriaPorPersona=UseFetchPOST(formData,'materiaporpersona/listar',true).data.data as unknown as InterfaceMateriaPorPersona[];
    const arrayMaterias=UseFetchPOST(formData,'materia/listar',true).data.data as unknown as InterfaceMateria[];
    const arrayPersonasProfesores=UseFetchPOST(formData,'persona/listarprofesores',true).data.data as unknown as InterfacePersona[];

    const [EselectMateria,setSelectMateria]=useState("");
    const [EselectProfesor,setSelectProfesor]=useState("");


    const handleChangeSelectMateria= (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectMateria(e.target.value);
    };

    const handleChangeSelectProfesor=(e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectProfesor(e.target.value);
    };

    function cambiarvistacrear(e: React.MouseEvent<HTMLButtonElement, MouseEvent>)
    {
        e;
        navigate("/crearmateriaporpersona");
    }

    return(
        <>
            <NavBarAdmin/>
            <button type="button" className="btn btn-warning m-3" onClick={cambiarvistacrear}>Crear</button>
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


            <div className="container mt-5">
                <table className="table table-sm">
                    <thead className="text-muted">
                        <tr>
                            <th scope="col" className="small">Materia</th>
                            <th scope="col" className="small">Curso</th>
                            <th scope="col" className="small">Persona</th>
                            <th scope="col" className="small">Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    {arrayMateriaPorPersona.map((elemento:InterfaceMateriaPorPersona)=> 
                    
                    <tr key={elemento.idmateriaporpersona}>
                        <td className="small">{elemento.idmateria.materia}</td>
                        <td className="small">{elemento.idmateria.idcurso.curso}</td>
                        <td className="small">{elemento.idpersona.nombres} {elemento.idpersona.apellidos}</td>
                        <td>
                            <SelectDiv 
                                nameops={["Editar"]}
                                links={[`/actualizarmateriaporpersona/${elemento.idmateriaporpersona}`]}
                                eventClick={[   
                                                (e)=>e,
                                                (e)=>e,
                                                (e)=>e,
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

export default ListarMateriaPorPersona;