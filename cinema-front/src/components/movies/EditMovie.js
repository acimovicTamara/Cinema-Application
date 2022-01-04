import React from 'react';
import CinemaAxios from './../../apis/CinemaAxios';
import {Col, Row, Form, Button} from 'react-bootstrap';
import {withParams, withNavigation} from '../../routeconf';

class EditMovie extends React.Component {

    constructor(props) {
        super(props);

        this.state = { movieId: -1, movieName: '', movieDuration: 0 }
    }

    componentDidMount() {
       this.getMovieById(this.props.params.id);
    }

    getMovieById(movieId) {
        CinemaAxios.get('/filmovi/' + movieId)
        .then(res => {
            // handle success
            console.log(res);
            this.setState({movieId: res.data.id, movieName: res.data.naziv, movieDuration: res.data.trajanje});
        })
        .catch(error => {
            // handle error
            console.log(error);
            alert('Error occured please try again!');
         });
    }

    onNameChange = event => {
        console.log(event.target.value);

        const { name, value } = event.target;
        console.log(name + ", " + value);

        this.setState((state, props) => ({
            movieName: value
        }));
    }

    onDurationChange = event => {
        console.log(event.target.value);

        const { name, value } = event.target;
        console.log(name + ", " + value);

        this.setState((state, props) => ({
            movieDuration: value
        }));
    }

    edit() {
        var params = {
            'id': this.state.movieId,
            'naziv': this.state.movieName,
            'trajanje': this.state.movieDuration
        };

        CinemaAxios.put('/filmovi/' + this.state.movieId, params)
        .then(res => {
            // handle success
            console.log(res);
            alert('Movie was edited successfully!');
            this.props.navigate('/movies');
        })
        .catch(error => {
            // handle error
            console.log(error);
            alert('Error occured please try again!');
         });
    }

    render() {
        return (
            <Col>
                <Row><h1>Edit movie</h1></Row>
                <Row>
                <Form>
                    <Form.Group>
                    <Form.Label htmlFor="name">Name</Form.Label>
                    <Form.Control id="name" type="text" value={this.state.movieName} onChange={(e) => this.onNameChange(e)}/><br/>
                    </Form.Group>
                    <Form.Group>
                    <Form.Label htmlFor="duration">Duration</Form.Label>
                    <Form.Control id="duration" type="number" value={this.state.movieDuration} onChange={(e) => this.onDurationChange(e)}/>
                    </Form.Group>
                </Form>
                </Row>
                <Button className="button button-navy" onClick={() => this.edit()}>Edit</Button>
            </Col>
        );
    }
}

export default withNavigation(withParams(EditMovie));