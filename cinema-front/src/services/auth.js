import CinemaAxios from "../apis/CinemaAxios"
import jwt_decode from 'jwt-decode';

export const login = async function(username, password){
    const cred = {
        username : username,
        password : password
    }
    try{
        const ret = await CinemaAxios.post('korisnici/auth', cred)
        const decoded_jwt = jwt_decode(ret.data);
        window.localStorage.setItem('role', decoded_jwt.role['authority']);
        window.localStorage.setItem('jwt',ret.data);
    }catch(err){
        console.log(err);
    }
    window.location.reload();
}

export const logout = async function(){
    window.localStorage.removeItem("jwt");
    window.localStorage.removeItem("role");
    window.location.reload();
}