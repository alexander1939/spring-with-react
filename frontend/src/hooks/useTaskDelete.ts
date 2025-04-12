import axios from "axios";
import { useState } from "react";


export const useTaskDelete = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
  
    const deleteTask = async (id: string) => {
      setLoading(true);
      setError(null);
      try {
        await axios.delete(`http://localhost:8080/api/tasks/delete-by-id?id=${id}`, {
          withCredentials: true,
        });
        return true;
      } catch (err) {
        setError("Error al eliminar la tarea");
        return false;
      } finally {
        setLoading(false);
      }
    };
  
    return { deleteTask, loading, error };
  };