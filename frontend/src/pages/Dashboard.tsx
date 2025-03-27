import { useAuthContext } from "../context/AuthContext";

const Dashboard = () => {
  const { logout } = useAuthContext();

  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <h1 className="text-2xl font-bold">Bienvenido al Dashboard</h1>
      <button onClick={logout} className="mt-4 bg-red-500 text-white py-2 px-4 rounded">
        Cerrar Sesión
      </button>
    </div>
  );
};

export default Dashboard;
