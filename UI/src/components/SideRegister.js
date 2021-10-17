import React, { useState, useEffect, component } from "react";
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
// import FormControlLabel from '@material-ui/core/FormControlLabel';
// import Checkbox from '@material-ui/core/Checkbox';
import Link from '@material-ui/core/Link';
import Paper from '@material-ui/core/Paper';
import Box from '@material-ui/core/Box';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import base_url from "../api/bootapi";
import axios from "axios";
import { ToastContainer ,toast } from "react-toastify";




function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
      <Link color="inherit" href="/components/SideRegister">
        Vaccine Tracker
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

const useStyles = makeStyles((theme) => ({
  root: {
    height: '100vh',
  },
  image: {
    backgroundImage: 'url(https://source.unsplash.com/1600x900/?vaccine)',
    backgroundRepeat: 'no-repeat',
    backgroundColor:
      theme.palette.type === 'light' ? theme.palette.grey[50] : theme.palette.grey[900],
    backgroundSize: 'cover',
    backgroundPosition: 'center',
  },
  paper: {
    margin: theme.spacing(8, 4),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.light,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

function SideRegister() {
  const classes = useStyles();
  
  
  const[postcall, setdata]=useState({name:"", email:"", pincode:""});

  const submit = (e)=>{
       e.preventDefault();
       console.log(postcall);
       axios.post(`${base_url}/add`, postcall).then(
            (response)=>{
                // document.getElementById("name").value = "";
                // document.getElementById("email").value = "";
                // document.getElementById("pincode").value = "";
                toast.success('Submitted!!', {
                  position: "top-center",
                  autoClose: 5000,
                  hideProgressBar: false,
                  closeOnClick: true,
                  pauseOnHover: false,
                  draggable: true,
                  progress: undefined,
                });
                  
            },
            (error)=>{
                toast.error('Something went wrong!!', {
                  position: "top-center",
                  autoClose: 5000,
                  hideProgressBar: false,
                  closeOnClick: true,
                  pauseOnHover: false,
                  draggable: true,
                  progress: undefined,
                  });  
            }
        )
    };
    
  const test=()=>{
    axios.get(`${base_url}/all`).then(
        (response)=>{
            console.log(response);
            toast.success('GET/ALL', {
              position: "top-center",
              autoClose: 5000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: false,
              draggable: true,
              progress: undefined,
            });
        },
        (error)=>{
          toast.error('Something went wrong!!', {
            position: "top-center",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: false,
            draggable: true,
            progress: undefined,
            });
        })

  }

    useEffect(()=>{
        test();
    },[]);

  return (
    <Grid container component="main" className={classes.root}>
      <CssBaseline />
      <Grid item xs={false} sm={4} md={7} className={classes.image} />
      <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
        <div className={classes.paper}>

            
          {/* <Grid container className={classes.root}>
            <Grid item >
                <Avatar className={classes.avatar}>
                    <LockOutlinedIcon  style={{ fontSize: 10 }} />
                </Avatar>
            </Grid>
            <Grid item >
                <Typography component="h3" variant="h5">
                    Register
                </Typography>
            </Grid>
          </Grid>
           */}

          <Avatar className={classes.avatar}>
              <LockOutlinedIcon />
          </Avatar>

          <Typography component="h1" variant="h5">
                VACCINE TRACKER
          </Typography>

          <form className={classes.form} onSubmit={submit} >

            <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                name="name"
                label="Name"
                type="text"
                id="name"
                onChange={(e)=> setdata({ ...postcall, name: e.target.value})}
                autoComplete="name"
                />

                <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                id="email"
                label="Email Address"
                name="email"
                onChange={(e)=> setdata({ ...postcall, email: e.target.value})}
                autoComplete="email"
                autoFocus
                />

                <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                name="pincode"
                label="Pincode"
                type="text"
                id="pincode"
                onChange={(e)=> setdata({ ...postcall, pincode: e.target.value})}
                autoComplete="pincode"
                />

                <ToastContainer />
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    color="primary"
                    className={classes.submit}
                >
                    Register
                </Button>

                <ToastContainer />
                <Button onClick={test}
                    > 
                    Click 
                </Button>
             <Box mt={5}>
                 <Copyright />
             </Box>
          </form>
        </div>
      </Grid>
    </Grid>
  );
}

export default SideRegister;
