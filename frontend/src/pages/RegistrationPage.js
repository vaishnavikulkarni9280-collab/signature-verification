import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const API_URL = "http://localhost:9090/api/auth/register";

function RegistrationPage() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    mobile: "",
    department: "",
    year: "",
    password: "",
    category: "student",
  });

  const [signatureFile, setSignatureFile] = useState(null);

  // Handle input text changes
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // Handle signature file upload
  const handleFileChange = (e) => {
    setSignatureFile(e.target.files[0]);
  };

  // Handle form submission
  const handleRegister = async (e) => {
    e.preventDefault();

    // Basic validation
    if (!formData.name || !formData.email || !formData.mobile || !formData.password || !formData.category) {
      alert("Please fill all required fields!");
      return;
    }

    try {
      // Prepare FormData for multipart/form-data
      const data = new FormData();
      for (const key in formData) {
        data.append(key, formData[key]);
      }
      if (signatureFile) {
        data.append("signatureImage", signatureFile);
      }

      // POST request to backend
      const response = await axios.post(API_URL, data, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      alert(response.data.message); // Show backend message
      navigate("/login"); // Redirect to login page
    } catch (error) {
      console.error(error);
      alert("Registration failed! Check console for details.");
    }
  };

  return (
    <div style={containerStyle}>
      <div style={formStyle}>
        <h2 style={headingStyle}>Register</h2>

        {/* Name */}
        <input
          type="text"
          name="name"
          placeholder="Enter your name"
          value={formData.name}
          onChange={handleChange}
          style={inputStyle}
          required
        />

        {/* Email */}
        <input
          type="email"
          name="email"
          placeholder="Enter your email"
          value={formData.email}
          onChange={handleChange}
          style={inputStyle}
          required
        />

        {/* Mobile */}
        <input
          type="tel"
          name="mobile"
          placeholder="Enter your mobile number"
          value={formData.mobile}
          onChange={handleChange}
          style={inputStyle}
          required
        />

        {/* Department */}
        <input
          type="text"
          name="department"
          placeholder="Enter your department"
          value={formData.department}
          onChange={handleChange}
          style={inputStyle}
        />

        {/* Year */}
        <input
          type="text"
          name="year"
          placeholder="Enter your year"
          value={formData.year}
          onChange={handleChange}
          style={inputStyle}
        />

        {/* Password */}
        <input
          type="password"
          name="password"
          placeholder="Enter your password"
          value={formData.password}
          onChange={handleChange}
          style={inputStyle}
          required
        />

        {/* Category */}
        <select
          name="category"
          value={formData.category}
          onChange={handleChange}
          style={inputStyle}
          required
        >
          <option value="student">Student</option>
          <option value="warden">Warden</option>
          <option value="hod">HOD</option>
        </select>

        {/* Signature File */}
        <input
          type="file"
          name="signatureImage"
          onChange={handleFileChange}
          style={inputStyle}
        />

        {/* Register Button */}
        <button onClick={handleRegister} style={buttonStyle}>
          Register
        </button>
      </div>
    </div>
  );
}

// Styling
const containerStyle = {
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  height: "100vh",
  backgroundColor: "#f0f2f5",
};

const formStyle = {
  backgroundColor: "white",
  padding: "30px",
  borderRadius: "10px",
  boxShadow: "0 8px 16px rgba(0, 0, 0, 0.1)",
  width: "320px",
  textAlign: "center",
};

const headingStyle = {
  color: "#222",
  marginBottom: "20px",
  fontWeight: "bold",
  fontSize: "22px",
};

const inputStyle = {
  width: "100%",
  padding: "10px",
  marginBottom: "10px",
  borderRadius: "6px",
  border: "1px solid #ccc",
  fontSize: "14px",
  boxSizing: "border-box",
};

const buttonStyle = {
  backgroundColor: "#28a745",
  color: "white",
  padding: "12px",
  border: "none",
  borderRadius: "6px",
  fontSize: "16px",
  cursor: "pointer",
  width: "100%",
  transition: "background-color 0.3s ease",
};

export default RegistrationPage;
