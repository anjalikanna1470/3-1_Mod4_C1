from fastapi import FastAPI
from pydantic import BaseModel
import uvicorn

app = FastAPI()

class TextRequest(BaseModel):
    text: str

class PipelineResponse(BaseModel):
    original: str
    summary: str
    category: str
    rewritten: str

# Helper function to simulate LLM inference
def run_llm_task(task_type: str, text: str):
    # In a real app, this is where you call your model
    # For demo purposes, we return formatted strings

    if task_type == "summarize":
        return f"Summary: {text[:50]}..." if len(text) > 50 else text
    elif task_type == "classify":
        if "code" in text.lower() or "software" in text.lower():
            return "Technology"
        elif "money" in text.lower() or "stock" in text.lower():
            return "Finance"
        else:
            return "General"
    elif task_type == "rewrite":
        return text.upper() # Simple rewrite logic

@app.post("/process", response_model=PipelineResponse)
async def process_text(request: TextRequest):
    input_text = request.text

    # 1. Summarize Task
    summary_result = run_llm_task("summarize", input_text)

    # 2. Classify Task (Uses original text)
    category_result = run_llm_task("classify", input_text)

    # 3. Rewrite Task (Uses original text)
    rewrite_result = run_llm_task("rewrite", input_text)

    return PipelineResponse(
        original=input_text,
        summary=summary_result,
        category=category_result,
        rewritten=rewrite_result
    )

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)