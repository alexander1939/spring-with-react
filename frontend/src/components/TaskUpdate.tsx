import { useState } from "react";
import { useUpdateTask } from "../hooks/UseTasksUpdate";
import { useAuthContext } from "../context/AuthContext";

interface EditModalProps {
  task: {
    id: string;
    title: string;
    description: string;
  };
  onClose: () => void;
  onSuccess: () => void;
}

export const EditTaskModal = ({ task, onClose, onSuccess }: EditModalProps) => {
  const { user } = useAuthContext();
  const [title, setTitle] = useState(task.title);
  const [description, setDescription] = useState(task.description);
  const { updateTask, loading, error } = useUpdateTask();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user) return;

    await updateTask({
      id: task.id,
      title,
      description,
      idUser: user.id,
    });

    onSuccess();
    onClose();
  };

  return (
    <div className="fixed inset-0 bg-white flex items-center justify-center z-50 px-4">
      <div className="bg-white rounded-3xl shadow-2xl p-8 w-full max-w-md transition-all duration-300">
        <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">
          Editar tarea
        </h2>

        <form onSubmit={handleSubmit} className="space-y-5">
          <div>
            <label className="block text-sm font-medium text-gray-600 mb-1">
              Título
            </label>
            <input
              type="text"
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
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="w-full px-4 py-2 rounded-xl bg-gray-100 border border-gray-300 shadow-inner h-28 resize-none focus:outline-none focus:ring-2 focus:ring-blue-300"
              required
            ></textarea>
          </div>

          <div className="flex justify-end gap-2">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 rounded-xl text-gray-700 bg-gray-200 hover:bg-gray-300 transition"
            >
              Cancelar
            </button>
            <button
              type="submit"
              disabled={loading}
              className={`px-4 py-2 rounded-xl text-white font-semibold transition duration-300 ${
                loading
                  ? "bg-blue-300 cursor-not-allowed"
                  : "bg-blue-500 hover:bg-blue-600 hover:shadow-lg"
              }`}
            >
              {loading ? "Guardando..." : "Guardar"}
            </button>
          </div>

          {error && (
            <p className="text-sm text-red-500 text-center">{error}</p>
          )}
        </form>
      </div>
    </div>
  );
};
