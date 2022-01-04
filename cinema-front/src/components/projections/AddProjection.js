import React from 'react';
import {Row, Col, Form, Button} from "react-bootstrap";
import CinemaAxios from '../../apis/CinemaAxios';
import {withNavigation} from '../../routeconf';

class AddProjection extends React.Component {

    constructor(props){
        super(props);

        let projection = {
            time: "",
            type: "",
            hall: 0,
            price: 0.00,
            movie: null
        }

        this.state = {projection: projection, movies: []};
    }

    componentDidMount(){
        this.getMovies();
    }

    // TODO: Dobaviti filmove
    async getMovies(){
        try{
            let result = await CinemaAxios.get("/filmovi");
            let movies = result.data;
            this.setState({movies: movies});
            console.log("test1");
        }catch(error){
            console.log(error);
            alert("Couldn't fetch movies");
        }
    }

    async create(e){
        e.preventDefault();

        try{

            let projection = this.state.projection;
            let projectionDTO = {
                datumIVreme: projection.time,
                film: projection.movie,
                sala: projection.hall,
                tip: projection.type,
                cenaKarte: projection.price
            }

            let response = await CinemaAxios.post("/projekcije", projectionDTO);
            this.props.navigate("/projections");
        }catch(error){
            alert("Couldn't save the movie");
        }
    }

    valueInputChanged(e) {
        let input = e.target;
    
        let name = input.name;
        let value = input.value;
    
        let projection = this.state.projection;
        projection[name] = value;
    
        this.setState({ projection: projection });
      }

    movieSelectionChanged(e){

        let movieId = e.target.value;
        let movie = this.state.movies.find((movie) => movie.id == movieId);

        let projection = this.state.projection;
        projection.movie = movie;

        this.setState({projection: projection});
    }

    render(){
        return (
            <>
            <Row>
                <Col></Col>
                <Col xs="12" sm="10" md="8">
                <h1>Add Projection</h1>

                <Form>
                    <Form.Group>
                    <Form.Label htmlFor="pTime">Time</Form.Label>
                    <Form.Control id="pTime" name="time" onChange={(e)=>this.valueInputChanged(e)}/> <br/>
                    </Form.Group>
                    
                    <Form.Group>
                    <Form.Label htmlFor="pType">Type</Form.Label>
                    <Form.Control id="pType" name="type" onChange={(e)=>this.valueInputChanged(e)}/> <br/>
                    </Form.Group>

                    <Form.Group>
                    <Form.Label id="pHall">Hall</Form.Label>
                    <Form.Control type="number" id="pHall" name="hall" onChange={(e)=>this.valueInputChanged(e)}/> <br/>
                    </Form.Group>

                    <Form.Group>
                    <Form.Label htmlFor="pPrice">Price</Form.Label>
                    <Form.Control type="number" step=".01" id="pPrice" name="price" onChange={(e)=>this.valueInputChanged(e)}/> <br/>
                    </Form.Group>

                    <Form.Group>
                    <Form.Label htmlFor="pMovie">Movies</Form.Label>
                    <Form.Control as="select" id="pMovie" onChange={event => this.movieSelectionChanged(event)}>
                        <option></option>
                        {
                            this.state.movies.map((movie) => {
                                return (
                                    <option key={movie.id} value={movie.id}>{movie.naziv}</option>
                                )
                            })
                        }
                    </Form.Control><br/>
                    </Form.Group>

                    <Button onClick={(event)=>{this.create(event);}}>Add</Button>
                </Form>
                </Col>
                <Col></Col>
            </Row>
            </>
        )
    }
}

export default withNavigation(AddProjection);