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

    if (result) {
      setTitle("");
      setDescription("");
      navigate("/tasks");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center">
      <div className="bg-white rounded-3xl shadow-2xl p-8 w-full max-w-md transition-all duration-300">
        <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">
          Crear nueva tarea
        </h2>

        <form onSubmit={handleSubmit} className="space-y-5">
          <div>
            <label className="block text-sm font-medium text-gray-600 mb-1">
              Título
            </label>
            <input
              type="text"
              placeholder="Escribe un título"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="w-full px-4 py-2 h-11 rounded-xl bg-gray-100 border border-gray-300 shadow-inner focus:outline-none focus:ring-2 focus:ring-blue-300"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-600 mb-1">
              Descripción
            </label>
            <textarea
              placeholder="Describe tu tarea"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="w-full px-4 py-2 rounded-xl bg-gray-100 border border-gray-300 shadow-inner h-28 resize-none focus:outline-none focus:ring-2 focus:ring-blue-300"
              required
            ></textarea>
          </div>

          <button
            type="submit"
            disabled={loading}
            className={`w-full py-3 rounded-xl text-white font-semibold transition duration-300 ${
              loading
                ? "bg-blue-300 cursor-not-allowed"
                : "bg-blue-500 hover:bg-blue-600 hover:shadow-lg"
            }`}
          >
            {loading ? "Creando..." : "Crear tarea"}
          </button>
        </form>

        {error && (
          <p className="text-sm text-red-500 text-center mt-4">{error}</p>
        )}
        {success && (
          <p className="text-sm text-green-600 text-center mt-4">
            ¡Tarea creada con éxito!
          </p>
        )}
      </div>
    </div>
  );
};

export default TaskForm;
