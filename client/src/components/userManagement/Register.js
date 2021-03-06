import React, { Component } from 'react';
import {createNewUser} from '../../actions/securityActions';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import classnames from 'classnames';
import FieldError from '../FieldError';

class Register extends Component {

    constructor(props) {
        super(props);

        this.state = {
            fullName: "",
            username: "",
            password: "",
            confirmPassword: "",

            errorObject: {
                errors: {}
            }
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onChange(e) {
        this.setState({[e.target.name]: e.target.value})
    }
    onSubmit(e) {
        e.preventDefault(); // prevent form from reloading
        const newUser = {
            fullName: this.state.fullName,
            username: this.state.username,
            password: this.state.password,
            confirmPassword: this.state.confirmPassword
        };
        this.props.createNewUser(newUser, this.props.history);
    }
    componentWillReceiveProps(nextProps) {
        this.setState({errorObject: nextProps.errorObject});
    }
    componentDidMount() {
        if (this.props.security.validToken) {
            this.props.history.push("/dashboard");
        }
    }

    render() {

        const {errors} = this.state.errorObject;

        return (
            <div className="register">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h1 className="display-4 text-center">Sign Up</h1>
                            <p className="lead text-center">Create your Account</p>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input 
                                    type="text"
                                    className={classnames({
                                        "form-control form-control-lg": true,
                                        "is-invalid": errors.fullName
                                    })}
                                    placeholder="Full name"
                                    name="fullName"
                                    value={this.state.fullName}
                                    onChange={this.onChange}/>
                                    {errors.fullName && (
                                        <div className="invalid-feedback"><FieldError errors={errors.fullName}/></div>
                                    )}
                                </div>
                                <div className="form-group">
                                    <input 
                                    type="email" 
                                    className={classnames({
                                        "form-control form-control-lg": true,
                                        "is-invalid": errors.username
                                    })}
                                    placeholder="Email Address" 
                                    name="username" 
                                    value={this.state.username}
                                    onChange={this.onChange}/>
                                    {errors.username && (
                                        <div className="invalid-feedback"><FieldError errors={errors.username}/></div>
                                    )}
                                </div>
                                <div className="form-group">
                                    <input 
                                    type="password" 
                                    className={classnames({
                                        "form-control form-control-lg": true,
                                        "is-invalid": errors.password
                                    })}
                                    placeholder="Password" 
                                    name="password"
                                    value={this.state.password}
                                    onChange={this.onChange}
                                    minLength={6}/>
                                    {errors.password && (
                                        <div className="invalid-feedback"><FieldError errors={errors.password}/></div>
                                    )}
                                </div>
                                <div className="form-group">
                                    <input 
                                    type="password" 
                                    className={classnames({
                                        "form-control form-control-lg": true,
                                        "is-invalid": errors.confirmPassword
                                    })}
                                    placeholder="Confirm Password"
                                    name="confirmPassword"
                                    value={this.state.confirmPassword}
                                    onChange={this.onChange}
                                    minLength={6}/>
                                    {errors.confirmPassword && (
                                        <div className="invalid-feedback"><FieldError errors={errors.confirmPassword}/></div>
                                    )}
                                </div>
                                <input type="submit" className="btn btn-info btn-block mt-4" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

Register.propTypes = {
    createNewUser: PropTypes.func.isRequired,
    errorObject: PropTypes.object.isRequired,
    security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    errorObject: state.errorObject,
    security: state.security
});

export default connect(
    mapStateToProps, 
    {createNewUser}
) (Register);
