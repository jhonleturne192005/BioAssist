import { useParams } from "react-router-dom";
import { ERROR_CAMPOS_VACIOS, KEY } from "../../Messages";
import { UseFetchPOST, UseFetchPOSTEvent } from "../../useFetch";
import { InterfaceDiaSemana } from "../../interfaces/InterfaceDiaSemana";
import { useState } from "react";
import { InterfaceHorario } from "../../interfaces/InterfaceHorario";
import { InterfaceMateriaPorPersona } from "../../interfaces/InterfaceMateriaPorPersona";
import toast from "react-hot-toast";

function Horario()
{
    const { idhorario } = useParams();

    const formData = new FormData();
    formData.append('key',String(localStorage.getItem(KEY)),);
    formData.append('idmateria',String(idhorario));
    
    //const arrayCurso=UseFetchPOST(formData,'curso/listar',false).data.data as unknown as InterfaceCurso[];
    const arrayDiaSemana=UseFetchPOST(formData,'diassemana/listar',false).data.data as unknown as InterfaceDiaSemana[];
    const arrayMateriaPorPersona=UseFetchPOST(formData,'materiaporpersona/listar',false).data.data as unknown as InterfaceMateriaPorPersona[];
    const arrayHorario=UseFetchPOST(formData,'horario/obtenerhorarioporid',false).data.data as unknown as InterfaceHorario[];


    const handleClickGuardar=(e:React.FormEvent<HTMLFormElement>)=>
    {
        e.preventDefault();
        console.log(e);
        const horaf1:string=(e.currentTarget.elements.namedItem('materia') as HTMLInputElement).value;
        const horaf2:string=(e.currentTarget.elements.namedItem('curso') as HTMLInputElement).value;
        const minutof1:string=(e.currentTarget.elements.namedItem('curso') as HTMLInputElement).value;
        const minutof2:string=(e.currentTarget.elements.namedItem('curso') as HTMLInputElement).value;
        const diasemana:string=(e.currentTarget.elements.namedItem('curso') as HTMLInputElement).value;
        const materiaporpersona:string=(e.currentTarget.elements.namedItem('curso') as HTMLInputElement).value;


        console.log(materiaporpersona);

        if(horaf1!="" && horaf2!="" && minutof1!="" && minutof2!="" && diasemana!="" && materiaporpersona!="")
        {
            const formData = new FormData();
            formData.append('hora_inicio',horaf1.trim());
            formData.append('hora_fin',horaf2.trim());
            formData.append('minuto_inicio',minutof1.trim());
            formData.append('minuto_fin',minutof2.trim());            
            formData.append('iddiassemana',diasemana.trim());
            formData.append('idmateriasporpersona',materiaporpersona.trim());
            console.log("Este es el curso= "+materiaporpersona);


            if(idhorario)
                formData.append('idhorario',idhorario);

            const promiseGuardarDatos=UseFetchPOSTEvent(formData,idhorario!=undefined?'horario/actualizar':'horario/crear');
            promiseGuardarDatos.then(data=>toast.success(data.data.successful))
            .catch(err=>toast.error(err.response.data.error));
        }
        else{
            toast.error(ERROR_CAMPOS_VACIOS);
        }
    }

    const [EselectMateriaPorPersona,setSelectMateriaPorPersona]=useState("");
    const [EselectDiaSemana,setSelectDiaSemana]=useState("");

    const handleChangeSelectMateriaPorPersona = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectMateriaPorPersona(e.target.value);
    };

    const handleChangeSelectDiaSemana = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectDiaSemana(e.target.value);
    };



    if(idhorario!=undefined)
    {
        setSelectMateriaPorPersona(String(arrayHorario[0].idmateriaporpersona));
        setSelectDiaSemana(String(arrayHorario[0].iddia.iddia));
    }
    



    return(
        <div className={''}>
            <form method="POST" name="form" className={''} onSubmit={handleClickGuardar} >
                <div className ={''}>
                    <label htmlFor="horaf1" className="form-label">Hora Inicio</label>
                    <input type="number" min={0} max={24} className="form-control" id="horaf1" required name="horaf1"  defaultValue={arrayHorario[0].hora_inicio} />
                </div>
                <div className ={''}>
                    <label htmlFor="minutof1" className="form-label">Minuto Inicio</label>
                    <input type="number" min={0}  max={60} className="form-control" id="minutof1" required name="minutof1"  defaultValue={arrayHorario[0].minuto_inicio} />
                </div>
                <div className ={''}>
                    <label htmlFor="horaf2" className="form-label">Hora Inicio</label>
                    <input type="number" min={0} max={24} className="form-control" id="horaf2" required name="horaf2"  defaultValue={arrayHorario[0].hora_fin} />
                </div>
                <div className ={''}>
                    <label htmlFor="minutof2" className="form-label">Minuto Inicio</label>
                    <input type="number" min={0}  max={60} className="form-control" id="minutof2" required name="minutof2"  defaultValue={arrayHorario[0].minuto_fin} />
                </div>
                <div className="mb-3">
                    <label htmlFor="diasemana" className="form-label">Dia Semana</label>
                    <select value={EselectDiaSemana} onChange={handleChangeSelectDiaSemana} id="diasemana" className="form-select" name="diasemana">
                        {arrayDiaSemana.map((elemento:InterfaceDiaSemana)=> <option key={elemento.iddia} value={elemento.iddia}>{elemento.dia}</option>)}
                    </select>
                </div>
                <div className="mb-3">
                    <label htmlFor="materiaporpersona" className="form-label">Materia Profesor</label>
                    <select value={EselectMateriaPorPersona} onChange={handleChangeSelectMateriaPorPersona} id="materiaporpersona" className="form-select" name="materiaporpersona">
                        {arrayMateriaPorPersona.map((elemento:InterfaceMateriaPorPersona)=> <option key={elemento.idmateriaporpersona} value={elemento.idmateriaporpersona}>{elemento.idpersona.nombres} {elemento.idpersona.apellidos}</option>)}
                    </select>
                </div>
                <button type="submit" className="btn btn-primary">Guardar</button>
            </form>
        </div>
    );
}

export default Horario;