import React from 'react';
import ReactDOM from 'react-dom';
import { Route, Link, HashRouter as Router, Routes, Navigate } from 'react-router-dom';
import { Navbar, Nav, Container, Button } from 'react-bootstrap';
import Home from './components/Home';
import Login from './components/authorization/Login'
import AddMovie from './components/movies/AddMovie';
import EditMovie from './components/movies/EditMovie';
import Movies from './components/movies/Movies';
import Projections from './components/projections/Projections';
import AddProjection from './components/projections/AddProjection';
import NotFound from './components/NotFound';
import { logout } from './services/auth';

class App extends React.Component {

    render() {
        const jwt = window.localStorage['jwt'];

        if (jwt) {
            return (
                <div>
                    <Router>
                        <Navbar expand bg="dark" variant="dark">
                            <Navbar.Brand as={Link} to="/">
                                JWD
                            </Navbar.Brand>
                            <Nav>
                                <Nav.Link as={Link} to="/movies">
                                    Movies
                                </Nav.Link>
                                <Nav.Link as={Link} to="/projections">
                                    Projections
                                </Nav.Link>
                                <Button onClick={() => logout()}>Log out</Button>
                            </Nav>
                        </Navbar>
                        <Container style={{ paddingTop: "10px" }}>
                            <Routes>
                                <Route path="/" element={<Home />} />
                                <Route path="/login" element={<Navigate replace to="/movies" />} />
                                <Route path="/movies" element={<Movies />} />
                                <Route path="/movies/add" element={<AddMovie />} />
                                <Route path="/movies/edit/:id" element={<EditMovie />} />
                                <Route path="/projections" element={<Projections />} />
                                <Route path="/projections/add" element={<AddProjection />} />
                                <Route path="*" element={<NotFound />} />
                            </Routes>
                        </Container>
                    </Router>
                </div>
            );
        }else{
            return (
                <div>
                    <Router>
                        <Navbar expand bg="dark" variant="dark">
                            <Navbar.Brand as={Link} to="/">
                                JWD
                            </Navbar.Brand>
                            <Nav>
                                <Nav.Link as={Link} to="/movies">
                                    Movies
                                </Nav.Link>
                                <Nav.Link as={Link} to="/login">
                                    Login
                                </Nav.Link>
                            </Nav>
                        </Navbar>
                        <Container style={{ paddingTop: "10px" }}>
                            <Routes>
                                <Route path="/movies" element={<Movies />} />
                                <Route path="/" element={<Home />} />
                                <Route path="/movies" element={<Movies />} />
                                <Route path="/login" element={<Login />} />
                                <Route path="*" element={<Navigate replace to="/login"/>} />
                            </Routes>
                        </Container>
                    </Router>
                </div>
            );
        }
    }
};


ReactDOM.render(
    <App />,
    document.querySelector('#root')
);
