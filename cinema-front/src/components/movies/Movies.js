import React from 'react';
import CinemaAxios from './../../apis/CinemaAxios';
import {Row, Col, Button, Table, Form} from 'react-bootstrap'
import './../../index.css';
import {withParams, withNavigation} from '../../routeconf'

class Movies extends React.Component {

    constructor(props) {
        super(props);

        this.state = { 
            movies: [],
            search: {
                name: "",
                minDuration: "",
                maxDuration: ""
            }
        }
    }

    componentDidMount() {
        this.getMovies();
    }

    getMovies() {
        const config = {
            params:{
                naziv:this.state.search.name,
                trajanjeOd:this.state.search.minDuration,
                trajanjeDo:this.state.search.maxDuration
            }
        }
        CinemaAxios.get('/filmovi', config)
            .then(res => {
                 // handle success
                 console.log(res);
                 this.setState({
                     movies: res.data,
                    });
            })
            .catch(error => {
                // handle error
                console.log(error);
                alert('Error occured please try again!');
            });
    }

    getGenresStringFromList(list) {
        return list.map(element => element.naziv).join(',');
    }

    renderMovies() {
        return this.state.movies.map((movie, index) => {
            return (
               <tr key={movie.id}>
                  <td>{movie.naziv}</td>
                  <td>{movie.trajanje}</td>
                  <td>{this.getGenresStringFromList(movie.zanrovi)}</td>
                  {window.localStorage.role=="ROLE_ADMIN"?
                  [<td><Button variant="warning" onClick={() => this.goToEdit(movie.id)}>Edit</Button></td>,
                  <td><Button variant="danger" onClick={() => this.delete(movie.id)}>Delete</Button></td>]
                    :null}
               </tr>
            )
         })
    }

    goToEdit(movieId) {
        this.props.navigate('/movies/edit/'+ movieId); 
    }

    deleteFromState(movieId) {
        var movies = this.state.movies;
        movies.forEach((element, index) => {
            if (element.id === movieId) {
                movies.splice(index, 1);
                this.setState({movies: movies});
            }
        });
    }

    delete(movieId) {
        CinemaAxios.delete('/filmovi/' + movieId)
        .then(res => {
            // handle success
            console.log(res);
            alert('Movie was deleted successfully!');
            this.deleteFromState(movieId); // ili refresh page-a window.location.reload();
        })
        .catch(error => {
            // handle error
            console.log(error);
            alert('Error occured please try again!');
         });
    }

    goToAdd() {
        this.props.navigate('/movies/add');  
    }

    onInputChange(event){
        const name = event.target.name;
        const value = event.target.value

        let search=this.state.search;
        search[name] = value;

        this.setState({search})
    }

    render() {
        return (
            <Col>
                <Row><h1>Movies</h1></Row>
                <Form>
                    <Row>
                        <Col>
                        <Form.Group>
                            <Form.Label>Naziv</Form.Label>
                            <Form.Control name="name" onChange={(e)=>this.onInputChange(e)}></Form.Control>
                        </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                        <Form.Group>
                            <Form.Label>Trajanje Od</Form.Label>
                            <Form.Control  value={this.state.search.minDuration} name="minDuration" onChange={(e)=>this.onInputChange(e)}></Form.Control>
                        </Form.Group>
                        </Col>
                        <Col>
                        <Form.Group>
                            <Form.Label>Trajanje Do</Form.Label>
                            <Form.Control name="maxDuration" onChange={(e)=>this.onInputChange(e)}></Form.Control>
                        </Form.Group>
                        </Col>
                    </Row>
                </Form>
                <Row>
                    <Button onClick={()=>this.getMovies()}>Search</Button>
                </Row>
                <br/>
                <Row>
                    <Button onClick={() => this.goToAdd() }>Add</Button>
                    <br/><br/>
                </Row>
                <Row>
                    <Table style={{marginTop:5}}>
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Duration (min)</th>
                                <th>Genres</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.renderMovies()}
                        </tbody>                  
                    </Table>
                </Row>
            </Col>
        );
    }
}

export default withNavigation(withParams(Movies));