import { useState  } from "react";
import axios from "axios";


interface GetTaskCreateData {
    title: string;
    description: string;
    idUser: string;
};


export const useGetTasksCreate = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState(false);


    const createTask = async ({ title, description, idUser }: GetTaskCreateData) => {
        setLoading(true);
        setError(null);
        setSuccess(false);

        try {
            const response = await axios.post("http://localhost:8080/api/tasks/create", {
                title,
                description,
                idUser
            },{
                withCredentials: true,
            });
            
            setSuccess(true);
            return response.data;
        } catch (err) {
            setError("Error al crear la tarea");
            return false;
        } finally {
            setLoading(false);
        }
    };

    return { createTask, loading, error, success };
}