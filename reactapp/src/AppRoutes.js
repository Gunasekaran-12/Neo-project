// // src/AppRoutes.js
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
// import { AuthProvider } from './context/AuthContext';
// import { ThemeProvider } from './context/ThemeContext';
// import ProtectedRoute from './components/ProtectedRoute';
// import Navbar from './components/Navbar';
// import Home from './pages/Home';
// import Login from './pages/Login';
// import Dashboard from './pages/Dashboard';
// import BookList from './components/BookList';
// import BookForm from './components/BookForm';
// import BorrowerList from './components/BorrowerList';
// import BorrowerForm from './components/BorrowerForm';
// import BorrowBook from './components/BorrowBook';
// import Reports from './components/Reports';
// import FineManagement from './components/FineManagement';
// import SearchBook from './components/SearchBook';

// function AppRoutes() {
//   return (
//     <Router>
//       <ThemeProvider>
//         <AuthProvider>
//           <Navbar />
//           <Routes>
//             {/* Public routes */}
//             <Route path="/" element={<Home />} />
//             <Route path="/login" element={<Login />} />
            
//             {/* Protected routes */}
//             <Route element={<ProtectedRoute />}>
//               <Route path="/dashboard" element={<Dashboard />} />
//               <Route path="/books" element={<BookList />} />
//               <Route path="/books/add" element={<BookForm />} />
//               <Route path="/books/edit/:id" element={<BookForm />} />
//               <Route path="/borrowers" element={<BorrowerList />} />
//               <Route path="/borrowers/add" element={<BorrowerForm />} />
//               <Route path="/borrowers/edit/:id" element={<BorrowerForm />} />
//               <Route path="/borrow" element={<BorrowBook />} />
//               <Route path="/reports" element={<Reports />} />
//               <Route path="/fines" element={<FineManagement />} />
//               <Route path="/search" element={<SearchBook />} />
//             </Route>
//           </Routes>
//         </AuthProvider>
//       </ThemeProvider>
//     </Router>
//   );
// }

// export default AppRoutes;