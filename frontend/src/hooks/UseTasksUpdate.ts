import { useState } from "react";
import axios from "axios";

interface UpdateTaskData {
  id: string;
  title: string;
  description: string;
  idUser: string;
}

export const useUpdateTask = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);

  const updateTask = async ({ id, title, description, idUser }: UpdateTaskData) => {
    setLoading(true);
    setError(null);
    setSuccess(false);

    try {
      const res = await axios.put("http://localhost:8080/api/tasks/update", {
        id,
        title,
        description,
        idUser,
      }, {
        withCredentials: true
      });
      setSuccess(true);
      return res.data;
    } catch (err) {
      setError("Error al actualizar la tarea");
    } finally {
      setLoading(false);
    }
  };

  return { updateTask, loading, error, success };
};
