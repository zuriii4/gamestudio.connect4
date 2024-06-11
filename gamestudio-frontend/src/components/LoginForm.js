import { Button, Col, Container, Form, Row } from "react-bootstrap";
import Modal from 'react-bootstrap/Modal';
import { useForm } from "react-hook-form";
import { useState } from "react";
import './LoginForm.css';

const availableColors = ["#006dc8", "#cf0000", "#c500cf", "#cab900", "#006400"];

const PlayerForm = ({ onSubmitLogin }) => {
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm();
    const [player1Color, setPlayer1Color] = useState(null);
    const [player2Color, setPlayer2Color] = useState(null);

    const handleColorChange = (player, color) => {
        if (player === "player1") {
            setPlayer1Color(color);
            if (player2Color === color) setPlayer2Color(null);
        } else if (player === "player2") {
            setPlayer2Color(color);
            if (player1Color === color) setPlayer1Color(null);
        }
    };

    const onSubmit = (data) => {
        if (!player1Color || !player2Color) {
            alert("Please select a color for both players.");
            return;
        }

        onSubmitLogin(data.player1, data.player2, player1Color, player2Color, data.row, data.column, data.mines );
    };

    return (
        <div
            className="modal show "
            style={{ display: 'block', position: 'initial' }}
        >
            <Modal.Dialog>
                <Modal.Body>
                    <h2 className={'text-black'}> Login </h2>
                    <div>
                        <Form onSubmit={handleSubmit(onSubmit)}>
                            <Container>
                                <Row>
                                    <Col>
                                        <Form.Group className="mb-3" controlId="formBasicPlayer1">
                                            <Form.Label className="text-black">Player 1 Name</Form.Label>
                                            <input
                                                className="form-control"
                                                type="text"
                                                {...register("player1", {
                                                    minLength: { value: 3, message: "Minimum is 3 characters" },
                                                    maxLength: { value: 50, message: "Maximum is 50 characters" },
                                                    required: { value: true, message: "Please enter a valid name" },
                                                })}
                                                placeholder="Player 1 Name"
                                            />
                                            <Form.Text style={{ color: "red", float: "right" }}>
                                                {errors.player1?.message}
                                            </Form.Text>
                                            <Form.Label className="float-left text-black mt-3 mb-3">Select Color</Form.Label>
                                            <div className="color-circles">
                                                {availableColors.map((color) => (
                                                    <label key={color}>
                                                        <input
                                                            type="radio"
                                                            name="player1Color"
                                                            value={color}
                                                            checked={player1Color === color}
                                                            onChange={() => handleColorChange("player1", color)}
                                                            disabled={player2Color === color}
                                                        />
                                                        <span className="color-circle" style={{ backgroundColor: color }}></span>
                                                    </label>
                                                ))}
                                            </div>
                                        </Form.Group>
                                    </Col>
                                    <Col>
                                        <Form.Group className="mb-3" controlId="formBasicPlayer2">
                                            <Form.Label className="text-black">Player 2 Name</Form.Label>
                                            <input
                                                className="form-control"
                                                type="text"
                                                {...register("player2", {
                                                    minLength: { value: 3, message: "Minimum is 3 characters" },
                                                    maxLength: { value: 50, message: "Maximum is 50 characters" },
                                                    required: { value: true, message: "Please enter a valid name" },
                                                })}
                                                placeholder="Player 2 Name"
                                            />
                                            <Form.Text style={{ color: "red", float: "right" }}>
                                                {errors.player2?.message}
                                            </Form.Text>
                                            <Form.Label className="float-left text-black mt-3 mb-3">Select Color</Form.Label>
                                            <div className="color-circles">
                                                {availableColors.map((color) => (
                                                    <label key={color}>
                                                        <input
                                                            type="radio"
                                                            name="player2Color"
                                                            value={color}
                                                            checked={player2Color === color}
                                                            onChange={() => handleColorChange("player2", color)}
                                                            disabled={player1Color === color}
                                                        />
                                                        <span className="color-circle" style={{ backgroundColor: color }}></span>
                                                    </label>
                                                ))}
                                            </div>
                                        </Form.Group>
                                    </Col>
                                </Row>
                                {/*board row column mines*/}
                                <Row>
                                    <Col md={4}>
                                        <Form.Group controlId="formBasicRow">
                                            <Form.Label>Rows</Form.Label>
                                            <input
                                                className="form-control"
                                                type="number"
                                                {...register("row", {
                                                    valueAsNumber: true,
                                                    min: { value: 6, message: "Minimum rows is 6" },
                                                    max: { value: 10, message: "Maximum rows is 10" },
                                                    required: "Row count is required"
                                                })}
                                                placeholder="Rows (6-10)"
                                            />
                                            <Form.Text className="text-danger">
                                                {errors.row?.message}
                                            </Form.Text>
                                        </Form.Group>
                                    </Col>
                                    <Col md={4}>
                                        <Form.Group controlId="formBasicColumn">
                                            <Form.Label>Columns</Form.Label>
                                            <input
                                                className="form-control"
                                                type="number"
                                                {...register("column", {
                                                    valueAsNumber: true,
                                                    min: { value: 4, message: "Minimum columns is 4" },
                                                    max: { value: 10, message: "Maximum columns is 10" },
                                                    required: "Column count is required"
                                                })}
                                                placeholder="Columns (4-10)"
                                            />
                                            <Form.Text className="text-danger">
                                                {errors.column?.message}
                                            </Form.Text>
                                        </Form.Group>
                                    </Col>
                                    <Col md={4}>
                                        <Form.Group controlId="formBasicMines">
                                            <Form.Label>Mines</Form.Label>
                                            <input
                                                className="form-control"
                                                type="number"
                                                {...register("mines", {
                                                    valueAsNumber: true,
                                                    min: { value: 0, message: "Minimum mines is 0" },
                                                    max: { value: 10, message: "Maximum mines is 10" },
                                                    required: "Mine count is required"
                                                })}
                                                placeholder="Mines (0-10)"
                                            />
                                            <Form.Text className="text-danger">
                                                {errors.mines?.message}
                                            </Form.Text>
                                        </Form.Group>
                                    </Col>
                                </Row>
                            </Container>
                            <Button variant="dark" type="submit" size="lg" className="mt-3">
                                Send
                            </Button>
                        </Form>
                    </div>
                </Modal.Body>
            </Modal.Dialog>
        </div>
    );
};

export default PlayerForm;
