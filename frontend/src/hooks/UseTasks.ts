import { useState, useEffect  } from "react";
import axios from "axios";


interface getTasksData {
    id: string;
    title: string;
    description: string;
};

export const useGetTasks = (userId: string) => {
    const[tasks, setTasks] = useState<getTasksData[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchGetTasks = async()=>{
            try{
                const response = await axios.get(`http://localhost:8080/api/tasks/get-all-by-id-user?id=${userId}`,
                    {
                        withCredentials: true,
                    }
                );
                if(!response.data){
                    throw new Error("No se encontraron tareas");
                }
                setTasks(response.data);
            }
            catch(err){
                setError("Error al obtener las tareas");
            }
            finally{
                setLoading(false);
            }
        };
        if(userId) fetchGetTasks();
    }, [userId]);
    return {tasks, loading, error};    
};