import React from "react";
import {Button, Table} from 'react-bootstrap';
import CinemaAxios from "../../apis/CinemaAxios";
import {withNavigation} from '../../routeconf';

class Projections extends React.Component {

  constructor(props) {
    super(props);

    this.state = { projections: [], movies: []};
    this.pageNo = 0;
    this.totalPages = 0;
  }

  componentDidMount() {
    this.getProjections(0);
  }

  async getProjections(newPageNo) {    
    const conf = {
      params : {
        pageNo: newPageNo
      }
    }
    try {
      let result = await CinemaAxios.get("/projekcije", conf);
      this.pageNo=newPageNo;
      this.totalPages = result.headers['total-pages'];
      this.setState({
         projections: result.data
        });
    } catch (error) {
      console.log(error);
    }
  }

  goToAdd() {
    this.props.navigate("/projections/add");
  }

  render() {
    return (
      <div>
        <h1>Projections</h1>

        <div>
          <Button onClick={() => this.goToAdd()}>
            Add
          </Button>
          <br />

          <br/>
          <br/>

          <Table id="movies-table">
            <thead>
              <tr>
                <th>Movie Name</th>
                <th>Time</th>
                <th>Projection Type</th>
                <th>Hall</th>
                <th>Price</th>
              </tr>
            </thead>
            <tbody>
              {this.state.projections.map((projection) => {
                return (
                  <tr key={projection.id}>
                    <td>{projection.film.naziv}</td>
                    <td>{projection.datumIVreme}</td>
                    <td>{projection.tip}</td>
                    <td>{projection.sala}</td>
                    <td>{projection.cenaKarte}</td>
                  </tr>
                );
              })}
            </tbody>
          </Table>
          <Button disabled={this.pageNo===0} 
                  onClick={()=>this.getProjections(this.pageNo-1)}
                  className="mr-2">Prev</Button>
          <Button disabled={this.pageNo==this.totalPages-1} onClick={()=>this.getProjections(this.pageNo+1)}>Next</Button>
        </div>
      </div>
    );
  }
}

export default withNavigation(Projections);
