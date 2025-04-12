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
    <div className="fixed inset-0 bg-white bg-opacity-30 flex items-center justify-center z-50">      
        <div className="bg-white p-6 rounded-lg shadow max-w-md w-full">
        <h2 className="text-lg font-bold mb-4">Editar tarea</h2>
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="p-2 border rounded"
            required
          />
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            className="p-2 border rounded"
            required
          />
          <div className="flex justify-end gap-2">
            <button
              type="button"
              onClick={onClose}
              className="px-3 py-1 bg-gray-500 text-white rounded"
            >
              Cancelar
            </button>
            <button
              type="submit"
              disabled={loading}
              className="px-3 py-1 bg-blue-600 text-white rounded hover:bg-blue-700"
            >
              {loading ? "Guardando..." : "Guardar"}
            </button>
          </div>
          {error && <p className="text-red-500">{error}</p>}
        </form>
      </div>
    </div>
  );
};
