import { useParams } from "react-router-dom";
import { ERROR_CAMPOS_VACIOS, KEY } from "../../Messages";
import { UseFetchPOST, UseFetchPOSTEvent } from "../../useFetch";
import { InterfaceTipoPersona } from "../../interfaces/InterfaceTipoPersona";
import { InterfacePersona } from "../../interfaces/InterfacePersona";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import NavBarAdmin from "../UtilsComponents/NavBarAdmin";
import styles from "../Curso/Curso.module.css"


function Persona()
{
    const { idpersona } = useParams();

    const formData = new FormData();
    formData.append('key',String(localStorage.getItem(KEY)),);
    formData.append('idpersona',String(idpersona));

    const arrayTipoPersona=UseFetchPOST(formData,'tipopersona/listar',true).data.data as unknown as InterfaceTipoPersona[];
    const arrayPersona=UseFetchPOST(formData,'persona/obtenerpersonaid',true).data.data as unknown as InterfacePersona[];


    const handleClickGuardar=(e:React.FormEvent<HTMLFormElement>)=>
    {
        e.preventDefault();
        console.log(e);
        const nombres:string=(e.currentTarget.elements.namedItem('nombres') as HTMLInputElement).value;
        const apellidos:string=(e.currentTarget.elements.namedItem('apellidos') as HTMLInputElement).value;
        const correo:string=(e.currentTarget.elements.namedItem('correo') as HTMLInputElement).value;
        const password:string=(e.currentTarget.elements.namedItem('password') as HTMLInputElement).value;
        const tipopersona:string=(e.currentTarget.elements.namedItem('tipopersona') as HTMLInputElement).value;

        console.log(tipopersona);

        if(nombres!="" && apellidos!="" && correo!="" && password!="" && tipopersona!="")
        {
            const formData = new FormData();
            formData.append('nombres',nombres.trim());
            formData.append('apellidos',apellidos.trim());
            formData.append('correo',correo.trim());
            formData.append('contrasenia',password.trim());            
            formData.append('idtipopersona',tipopersona.trim());
            console.log("Este es el tipo persona= "+tipopersona);


            if(idpersona)
                formData.append('idpersona',idpersona);

            const promiseGuardarDatos=UseFetchPOSTEvent(formData,idpersona!=undefined?'persona/actualizardatosadmin':'persona/crear');
            promiseGuardarDatos.then(data=>toast.success(data.data.successful))
            .catch(err=>toast.error(err.response.data.error));
        }
        else{
            toast.error(ERROR_CAMPOS_VACIOS);
        }
    }


    const [EselectTipoPersona,setSelectTipoPersona]=useState("");

    const handleChangeSelectTipoPersona = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectTipoPersona(e.target.value);
    };


    useEffect(() => {
        setSelectTipoPersona(String(arrayPersona.length>0?arrayPersona[0].idtipopersona.idtipopersona:0));
    }, [arrayPersona]);

    return(
        <>
            <NavBarAdmin/>
            <div className={styles.container_registro}>
                <form method="POST" name="form" className={styles.formWi} onSubmit={handleClickGuardar} >
                    <div className="mb-3 controles_input">
                        <label htmlFor="nombres" className="form-label"><b>Nombres</b></label>
                        <input type="text" defaultValue={arrayPersona.length>0?arrayPersona[0].nombres:""} className="form-control" id="nombres" maxLength={100} required name="nombres" />
                    </div>
                    <div className="mb-3 controles_input">
                        <label htmlFor="apellidos" className="form-label"><b>Apellidos</b></label>
                        <input type="text" defaultValue={arrayPersona.length>0?arrayPersona[0].apellidos:""} className="form-control" id="apellidos" maxLength={100} required name="apellidos" />
                    </div>
                    <div className="mb-3 controles_input">
                        <label htmlFor="correo" className="form-label"><b>Correo</b></label>
                        <input type="email" defaultValue={arrayPersona.length>0?arrayPersona[0].correo:""} className="form-control" id="correo" maxLength={100} required name="correo" />
                    </div>
                    <div className="mb-3 controles_input">
                        <label htmlFor="password" className="form-label"><b>Contraseña</b></label>
                        <input type="password" defaultValue={arrayPersona.length>0?arrayPersona[0].contrasenia:""} className="form-control" id="password" maxLength={100} required name="password" />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="tipopersona" className="form-label">Tipo</label>
                        <select value={EselectTipoPersona} onChange={handleChangeSelectTipoPersona} id="tipopersona" className="form-select" name="tipopersona">
                            {arrayTipoPersona.map((elemento:InterfaceTipoPersona)=> <option key={elemento.idtipopersona} value={elemento.idtipopersona}>{elemento.tipo}</option>)}
                        </select>
                    </div>
                    <button type="submit" className="btn btn-primary">Guardar</button>
                </form>
            </div>
        </>
    );
}

export default Persona;
