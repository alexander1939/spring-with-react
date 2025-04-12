import { useGetTasks } from "../hooks/UseTasks";
import { EditTaskModal } from "../components/TaskUpdate";
import { useTaskDelete } from "../hooks/useTaskDelete";
import { useState } from "react";

interface TaskListProps {
  userId: string;
}

export const TaskList = ({ userId }: TaskListProps) => {
  const { tasks, loading, error } = useGetTasks(userId);
  const { deleteTask } = useTaskDelete();
  const [editingTask, setEditingTask] = useState<{ id: string; title: string; description: string } | null>(null);

  const handleDelete = async (id: string) => {
    const confirm = window.confirm("¿Estás seguro de que deseas eliminar esta tarea?");
    if (!confirm) return;

    const success = await deleteTask(id);
    if (success) {
      window.location.reload(); 
    }
  };

  if (loading) return <p>Cargando...</p>;
  if (error) return <p>{error}</p>;

  const formatDescription = (text: string, maxLineLength: number = 25): string => {
    const words = text.split(' ');
    let currentLineLength = 0;
    let formattedText = '';

    words.forEach(word => {
      if (currentLineLength + word.length > maxLineLength) {
        formattedText += '\n';
        currentLineLength = 0;
      }
      formattedText += word + ' ';
      currentLineLength += word.length + 1;
    });

    return formattedText.trim();
  };

  return (
    <div className="p-4 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
      {tasks.length === 0 && <p>No hay tareas</p>}
      {tasks.map((task) => (
        <div
          key={task.id}
          className="flex flex-row bg-white border border-gray-200 rounded-lg shadow-sm hover:bg-gray-100 dark:border-gray-700 dark:bg-gray-800 dark:hover:bg-gray-700"
        >
          <img
            className="object-cover w-48 h-auto rounded-l-lg"
            src="https://flowbite.com/docs/images/blog/image-1.jpg"
            alt=""
          />
          <div className="flex flex-col justify-between p-4 flex-1">
            <div>
              <h5 className="text-xl font-bold text-gray-900 dark:text-white">{task.title}</h5>
              <p
                className="text-gray-700 dark:text-gray-400 mt-2 whitespace-pre-wrap"
                style={{
                  wordBreak: 'break-word',
                  overflowWrap: 'break-word',
                  maxWidth: '100%',
                }}
              >
                {formatDescription(task.description)}
              </p>
            </div>
            <div className="flex gap-2 mt-4">
              <button
                className="px-3 py-1 bg-yellow-500 text-white rounded hover:bg-yellow-600"
                onClick={() => setEditingTask(task)}
              >
                Editar
              </button>
              <button
                className="px-3 py-1 bg-red-500 text-white rounded hover:bg-red-600"
                onClick={() => handleDelete(task.id)}
              >
                Eliminar
              </button>
            </div>
          </div>
        </div>
      ))}

      {editingTask && (
        <EditTaskModal
          task={editingTask}
          onClose={() => setEditingTask(null)}
          onSuccess={() => {
            setEditingTask(null);
            window.location.reload(); 
          }}
        />
      )}
    </div>
  );
};
