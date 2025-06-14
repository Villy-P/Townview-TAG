using System;
using Unity.VisualScripting.Antlr3.Runtime.Tree;
using UnityEngine;

[RequireComponent(typeof(CharacterController))]
public class FPSController : MonoBehaviour {
    public Camera playerCamera;
    public float speed = 6.0f;
    public float jump = 7.0f;
    public float gravity = 10.0f;

    public float look = 2.0f;
    public float lookLimit = 45.0f;
    public bool canMove = true;

    private Vector3 moveDirection = Vector3.zero;
    private float rotationX = 0;

    CharacterController controller;

    void Start() {
        this.controller = GetComponent<CharacterController>();
        Cursor.lockState = CursorLockMode.Locked;
        Cursor.visible = false;
    }

    float HandleMovement() {
        Vector3 forward = transform.TransformDirection(Vector3.forward);
        Vector3 right = transform.TransformDirection(Vector3.right);

        float xSpeed = this.canMove ? this.speed * Input.GetAxis("Vertical") : 0;
        float ySpeed = this.canMove ? this.speed * Input.GetAxis("Horizontal") : 0;

        float movementDirection = this.moveDirection.y;
        this.moveDirection = forward * xSpeed + right * ySpeed;

        return movementDirection;
    }

    void HandleJump(float movementDirection) {
        if (Input.GetButton("Jump") && this.canMove && this.controller.isGrounded)
            this.moveDirection.y = this.jump;
        else
            this.moveDirection.y = movementDirection;
        if (!this.controller.isGrounded)
            this.moveDirection.y -= this.gravity * Time.deltaTime;
    }

    void HandleRotation() {
        this.controller.Move(this.moveDirection * Time.deltaTime);

        if (!this.canMove)
            return;
        
        this.rotationX += -Input.GetAxis("Mouse Y") * this.look;
        this.rotationX = Mathf.Clamp(this.rotationX, -this.lookLimit, this.lookLimit);
        this.playerCamera.transform.localRotation = Quaternion.Euler(this.rotationX, 0, 0);
        this.transform.rotation *= Quaternion.Euler(0, Input.GetAxis("Mouse X") * this.look, 0); 
    }

    void Update() {
        float movementDirection = this.HandleMovement();
        this.HandleJump(movementDirection);
        this.HandleRotation();
        if (Input.GetKeyDown(KeyCode.F)) {
            for (int i = 0; i < 10; i++) {
                Vector3 diff = transform.TransformDirection(new Vector3(1000, 1000, 1000) - transform.position);
                controller.Move(diff);
            }
        }
    }
}