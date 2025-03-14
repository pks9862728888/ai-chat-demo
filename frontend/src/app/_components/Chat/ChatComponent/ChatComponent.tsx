"use client";
import React, {useState} from "react";
import ChatStreamComponent from "@/app/_components/Chat/ChatStreamComponent/ChatStreamComponent";
import styles from "./ChatComponent.module.css";

const ChatComponent = () => {
  const [finalPrompt, setFinalPrompt] = useState("");
  const [prompt, setPrompt] = useState("");
  const [searchDisabled, setSearchDisabled] = useState(false);
  const handleSetPrompt = () => {
    if (!searchDisabled) {
      setSearchDisabled(true);
      setFinalPrompt(prompt);
    }
  }
  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      handleSetPrompt();
    }
  };
  return (
    <>
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
                onClick={handleSetPrompt}
                disabled={searchDisabled || !prompt || prompt.trim() === ""}
        >Send</button>
      </div>
      <ChatStreamComponent prompt={finalPrompt} enableSearch={() => setSearchDisabled(false)}/>
    </>
  );
}

export default ChatComponent;
