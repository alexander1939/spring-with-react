import { useState } from "react";
import { useAuthContext } from "../context/AuthContext";
import { useGetTasksCreate } from "../hooks/useTasksCreate";
import { useNavigate } from "react-router-dom";

const TaskForm = () => {
  const { user } = useAuthContext();
  const { createTask, loading, error, success } = useGetTasksCreate();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user) return;

    const result = await createTask({
      title,
      description,
      idUser: user.id,
    });

    setTitle("");
    setDescription("");
    if (result) {
        setTitle("");
        setDescription("");
        navigate("/tasks");
      }
  };

  return (
    <div className="max-w-md mx-auto mt-10 p-4 bg-white rounded-xl shadow">
      <h2 className="text-xl font-bold mb-4">Crear nueva tarea</h2>
      <form onSubmit={handleSubmit} className="flex flex-col gap-4">
        <input
          type="text"
          placeholder="Título"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          className="p-2 border rounded"
          required
        />
        <textarea
          placeholder="Descripción"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          className="p-2 border rounded"
          required
        ></textarea>
        
        <button
          type="submit"
          disabled={loading}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          {loading ? "Creando..." : "Crear tarea"}
        </button>
      </form>
      {error && <p className="text-red-500 mt-2">{error}</p>}
      {success && <p className="text-green-600 mt-2">¡Tarea creada con éxito!</p>}
    </div>
  );
};

export default TaskForm;
