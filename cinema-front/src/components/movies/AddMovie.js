import React from "react";
import { Form, Button, Row, Col, InputGroup } from "react-bootstrap";
import CinemaAxios from "./../../apis/CinemaAxios";
import SelectGenres from "./SelectGenres";
import { withNavigation } from "../../routeconf";

class AddMovie extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      name: "",
      duration: 0,
      showSelectGenres: false,
      selectedGenres: [],
    };
    this.create = this.create.bind(this);
    this.handleGenreSelection = this.handleGenreSelection.bind(this);
  }

  handleGenreSelection(selectedGenres) {
    this.setState({ selectedGenres: selectedGenres });
  }

  create() {
    var params = {
      naziv: this.state.name,
      trajanje: this.state.duration,
      zanrovi: this.state.selectedGenres
    };

    CinemaAxios.post("/filmovi", params)
      .then((res) => {
        // handle success
        console.log(res);

        alert("Movie was added successfully!");
        this.props.navigate("/movies");
      })
      .catch((error) => {
        // handle error
        console.log(error);
        alert("Error occured please try again!");
      });
  }

  onNameChange = (event) => {
    console.log(event.target.value);

    const { name, value } = event.target;
    console.log(name + ", " + value);

    this.setState((state, props) => ({
      name: value,
    }));
  };

  onDurationChange = (event) => {
    console.log(event.target.value);

    const { name, value } = event.target;
    console.log(name + ", " + value);

    this.setState((state, props) => ({
      duration: value,
    }));
  };

  summarizedGenres(){
    
    return this.state.selectedGenres.map(element => element.naziv).join(',');
  }

  render() {
    return (
      <>
        <Row>
          <Col></Col>
          <Col xs="12" sm="10" md="8">
            <h1>Add new movie</h1>
            <Form>
              <Form.Label htmlFor="name">Name</Form.Label>
              <Form.Control
                id="name"
                type="text"
                onChange={(e) => this.onNameChange(e)}
              />
              <Form.Label htmlFor="duration">Duration</Form.Label>
              <Form.Control
                id="duration"
                type="number"
                onChange={(e) => this.onDurationChange(e)}
              />

              <Form.Label>Genres</Form.Label>
              <InputGroup>
                <Form.Control value={this.summarizedGenres()} disabled />
                <InputGroup.Append>
                  <Button
                    variant="info"
                    onClick={() => this.setState({ showSelectGenres: true })}
                  >
                    &gt;
                  </Button>
                </InputGroup.Append>
              </InputGroup>

              <Button style={{ marginTop: "25px" }} onClick={this.create}>
                Add
              </Button>
            </Form>
          </Col>
          <Col></Col>
        </Row>

        <SelectGenres
          show={this.state.showSelectGenres}
          handleClose={() => this.setState({ showSelectGenres: false })}
          selectedGenres={this.state.selectedGenres}
          finishSelection={(newlySelectedGenres) =>
            this.setState({
              selectedGenres: newlySelectedGenres,
              showSelectGenres: false,
            })
          }
        />
      </>
    );
  }
}

export default withNavigation(AddMovie);
