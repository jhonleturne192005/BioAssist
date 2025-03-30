import { Link, useNavigate } from "react-router-dom";
import { KEY } from "../../Messages";

function NavBar() {


  const navigate=useNavigate();


  function cerrar_session(e: React.MouseEvent<HTMLButtonElement, MouseEvent>)
  {
    e;
    e.preventDefault();
    localStorage.removeItem(KEY);
    localStorage.removeItem("admin");
    navigate("/");
  }

  const stylebackgroundColor = {
    backgroundColor: "#343A40",
  }

  const styleLetrasColor = {
    color: "#9A9D9A"
  }

  const styleLetrasColorWhite = {
    color: "#FFF"
  }

  return (

    <nav className="navbar navbar-expand-lg " style={stylebackgroundColor}>
      <div className="container-fluid"  >
        <a className="navbar-brand" href="#" style={styleLetrasColorWhite}>
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
              <Link to={"/materiasprofesor"} className="nav-link active" style={styleLetrasColor}>
                Asistencias
              </Link>
            </li>
            
            <li className="nav-item">
              <Link to={"/reconocimiento"} className="nav-link active" style={styleLetrasColor}>
                Entrenamiento (prueba)
              </Link>
            </li>
          </ul>
          <form className="d-flex" role="search">
            <button className="btn btn-success"  style={styleLetrasColorWhite} type="submit" onClick={cerrar_session}>
              Cerrar Session
            </button>
          </form>
        </div>
      </div>
    </nav>
  );
}

export default NavBar;
