import { Link, useNavigate } from "react-router-dom";
import { KEY } from "../../Messages";

function NavBarAdmin() {

  const navigate=useNavigate();


  function cerrar_session(e: React.MouseEvent<HTMLButtonElement, MouseEvent>)
  {
    e;
    e.preventDefault();
    localStorage.removeItem(KEY);
    localStorage.removeItem("admin");
    navigate("/");
  }



  return (
    <nav className="navbar navbar-expand-lg bg-body-tertiary">
      <div className="container-fluid">
        <a className="navbar-brand" href="#">
            BioAssist
        </a>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarScroll"
          aria-controls="navbarScroll"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarScroll">
          <ul className="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll">
            <li className="nav-item">
              <Link to={"/listarcompetencias"} className="nav-link active">
                Competencias
              </Link>
            </li>
            <li className="nav-item">
              <Link to={"/"} className="nav-link active">
                Eventos
              </Link>
            </li>
            <li className="nav-item">
              <Link to={"/"} className="nav-link active">
                Reportes
              </Link>
            </li>
            <li className="nav-item dropdown">
                <a className="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Utilidades
                </a>
                <ul className="dropdown-menu">
                    <li><a className="dropdown-item" href="#">Genero</a></li>
                    <li><a className="dropdown-item" href="#">Modalidad</a></li>
                    <li><a className="dropdown-item" href="#">Cinturon</a></li>
                    <li><a className="dropdown-item" href="#">Categorias</a></li>
                    <li><a className="dropdown-item" href="#">Pesos</a></li>
                    <li><hr className="dropdown-divider" /></li>
                    <li><a className="dropdown-item" href="#">Registro Ayuda</a></li>
                </ul>
            </li>
          </ul>
          <form className="d-flex" role="search">
            <button className="btn btn-outline-success" type="submit" onClick={cerrar_session}>
              Cerrar Session
            </button>
          </form>
        </div>
      </div>
    </nav>
  );
}

export default NavBarAdmin;
