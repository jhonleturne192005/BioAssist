import { BrowserRouter, Routes,Route } from "react-router-dom";
import ProtectedRoute from "./components/UtilsComponents/ProtectedRoute";
import Login from "./components/Login/Login";
import Curso from "./components/Curso/Curso";
import Materia from "./components/Materia/Materia";
import ListarCurso from "./components/Curso/ListarCurso";
import ListarMateria from "./components/Materia/ListarMateria";
import Persona from "./components/Persona/Persona";
import ListarPersona from "./components/Persona/ListarPersona";
import Home from "./components/home/Home";
import MateriaPorPersona from "./components/Materia/MateriaPorPersona";
import ListarMateriaPorPersona from "./components/Materia/ListarMateriaPorPersona";
import Entrenamiento from "./components/Reconocimiento/Entrenamiento";
import ListarMatriculados from "./components/Asistencia/ListarMatriculados";
import ListarMateriasProfesor from "./components/Asistencia/ListarMateriasProfesor";


//https://react-hot-toast.com/ --> para el toast
//https://www.youtube.com/watch?v=dGYs8I9XY98


function App() {
  
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/home" element={<Home />} />
        <Route path="/matriculados/:idmateriasporpersona" element={<ListarMatriculados />} />
        <Route path="/materiasprofesor" element={<ListarMateriasProfesor />} />
        <Route element={<ProtectedRoute/>}>
          <Route path="/crearcurso" element={<Curso />} />
          <Route path="/listarcurso" element={<ListarCurso />} />
          <Route path="/actualizarcurso/:idcurso" element={<Curso />} />
          <Route path="/crearmateria" element={<Materia />} />
          <Route path="/listarmateria" element={<ListarMateria />} />
          <Route path="/actualizarmateria/:idmateria" element={<Materia />} />
          <Route path="/crearmateriaporpersona" element={<MateriaPorPersona />} />
          <Route path="/listarmateriaporpersona" element={<ListarMateriaPorPersona />} />
          <Route path="/actualizarmateriaporpersona/:idmateriaporpersona" element={<MateriaPorPersona />} />



          <Route path="/crearpersona" element={<Persona />} />
          <Route path="/listarpersona" element={<ListarPersona />} />
          <Route path="/actualizarpersona/:idpersona" element={<Persona />} />
          <Route path="/reconocimiento" element={<Entrenamiento />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App
