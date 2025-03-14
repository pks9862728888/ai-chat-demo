import styles from "@/app/_components/Chat/ChatComponent/ChatComponent.module.css";
import React, {useState} from "react";

const ChatSearchInput = ({searchDisabled, handleSetPrompt}:
                         { searchDisabled: boolean, handleSetPrompt: (prompt: string) => void }) => {
  const [prompt, setPrompt] = useState("");
  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      handleSetPrompt(prompt);
    }
  };
  return (
    <div className={styles.searchContainer}>
      <input
        className={styles.input}
        type="text"
        value={prompt}
        onKeyDown={handleKeyDown}
        onChange={(e) => setPrompt(e.target.value)}
        placeholder="Enter your prompt"
      />
      <button className={`btnPrimary ${searchDisabled || !prompt || prompt.trim() === "" ? "btnPrimaryDisabled" : ""}`}
              onClick={() => handleSetPrompt(prompt)}
              disabled={searchDisabled || !prompt || prompt.trim() === ""}
      >Send
      </button>
    </div>
  );
}

export default ChatSearchInput;
