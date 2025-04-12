import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import Dashboard from "./pages/Dashboard";
import RegisterPage from "./pages/RegisterPage";
import { AuthProvider, useAuthContext } from "./context/AuthContext";
import { ReactNode } from "react";
import TasksPage from "./pages/TasksListPage";
import CreateTaskPage  from "./pages/CreateTaskPage";

const PrivateRoute = ({ children }: { children: ReactNode  }) => {
  const { isAuthenticated } = useAuthContext();
  return isAuthenticated ? children : <Navigate to="/login" />;
};

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
          <Route path="/tasks" element={<PrivateRoute> <TasksPage /></PrivateRoute>} />
          <Route path="/tasks/create" element={<PrivateRoute> <CreateTaskPage /></PrivateRoute>} />
          <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
};

export default App;
