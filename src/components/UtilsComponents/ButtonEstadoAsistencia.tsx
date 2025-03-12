import styles from './AccessUtils.module.css'

interface ButtonProps{
    elementoValue:boolean
}


function ButtonEstadoAsistencia(props:ButtonProps)
{
    const {elementoValue} =props

    return (
        <button id={styles.buttonEstado} type="button" className={`btn ${elementoValue ? "btn-success" : "btn-danger"}` }>{elementoValue?'✓':'X'} </button>
    );
}

export default ButtonEstadoAsistencia;