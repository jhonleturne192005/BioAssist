import styles from './AccessUtils.module.css'

interface ButtonProps{
    elementoValue:boolean
}


function ButtonEstado(props:ButtonProps)
{
    const {elementoValue} =props

    return (
        <button id={styles.buttonEstado} type="button" className={`btn ${elementoValue ? "btn-success" : "btn-danger"}` }>{elementoValue?'Activo':'Inactivo'} </button>
    );
}

export default ButtonEstado;