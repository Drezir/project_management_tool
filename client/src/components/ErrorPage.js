import React, { Component } from 'react'

export default class ErrorPage extends Component {
    render() {
        const { errorItems, stacktrace } = this.props.errors;
        return (
            <div className="container">
            <p className="display-4">Errors:</p>
                <ul>
                    {
                        Object.keys(errorItems).map(function (key) {
                            const error = errorItems[key];
                            return (
                                <li>
                                    {key}
                                    <ul>
                                        <li>{error}</li>
                                    </ul>
                                </li>
                            );
                        })
                    }
                </ul>
                <div className="mt-4">
                    <p className="display-4">Stacktrace:</p>
                    <textarea readOnly={true} className="w-100 stacktrace" >
                        {stacktrace}
                    </textarea>
                </div>
            </div>
        )
    }
}