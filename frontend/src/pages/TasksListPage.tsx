import { useAuthContext } from "../context/AuthContext";
import {TaskList} from "../components/TaskList";
import { useNavigate } from "react-router-dom";

const TasksPage = () => {
  const { user, isAuthenticated } = useAuthContext();
  const navigate = useNavigate();
  if (!isAuthenticated || !user) return <p className="text-red-500">Debes iniciar sesión.</p>;

  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <h1 className="text-3xl font-bold mb-4">Tareas de {user.name}</h1>
      <p className="text-lg mb-4">Bienvenido a tu lista de tareas.</p>
      <button onClick={() => navigate("/tasks/create")} className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 mb-4">
        Crear nueva tarea

      </button>
      <TaskList userId={user.id} />
    </div>
  );
};

export default TasksPage;
