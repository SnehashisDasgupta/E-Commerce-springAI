# 🛒 E-Bazar — AI-Powered E-Commerce Platform

**E-Bazar** is an intelligent and modern **Spring Boot–based E-Commerce platform** integrated with **Spring AI** to provide smart product insights, recommendations, and assistance — all while being **completely free**.  
The unique aspect of this project is that it runs **entirely on open-source AI models**, with **no paid APIs** like OpenAI or Hugging Face required.

---

## 🌟 Unique Selling Point (USP)

> 🧠 **E-Bazar uses only free and open-source AI models!**  
> This means you can run the complete AI-powered E-Commerce platform **locally and free of cost with Ollama**, and free models of hugging face for image generation using API keys.  
> Built with **Spring AI + pgVector + Ollama**, it provides intelligent, context-aware product recommendations using **fully local embeddings and models**.

---

## 🚀 Features

- 🧠 **AI-Based Recommendations** — Product suggestions powered by open-source models.  
- 🛍️ **Product Management** — Add, update, and delete products dynamically.  
- 🧾 **Category & Stock Handling** — Manage product categories and stock availability.  
- 💬 **AI Chat Support (Free Models)** — Built-in AI assistant to guide customers.  
- 💻 **Responsive UI** — Built using React + Tailwind CSS for a sleek, adaptive interface.  
- 🔒 **Authentication & Authorization** — Secure login and signup flow.  
- ⚙️ **Completely Free** — No paid API usage; 100% open and self-hosted.

---

## 🧩 Tech Stack

**Backend**
- Spring Boot  
- Spring MVC  
- Spring Data JPA  
- Spring AI  
- PostgreSQL + pgVector  

**Frontend**
- React.js  
- Tailwind CSS  

**Tools**
- Maven  
- Axios (for API requests)  
- Toastify (for notifications)  

---

## 🐘 PostgreSQL + pgVector Configuration

The project uses **PostgreSQL with pgVector** for AI embeddings and vector search.  
Use Docker to easily set up the database environment.

### 🧱 Docker Configuration

Add this configuration to your `docker-compose.yml` file:

```yaml
services:
  pgvector:
    image: 'pgvector/pgvector:pg16'
    environment:
      - 'POSTGRES_DB=XXXXXXXXXXXX'
      - 'POSTGRES_USER=xxxxxx'
      - 'POSTGRES_PASSWORD=xxxxxxxxxxx'
    labels:
      - "org.springframework.boot.service-connection=postgres"
    ports:
      - '5432:5432'
