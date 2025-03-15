"use client";
import React, {useState} from "react";
import ChatLogComponent from "@/app/_components/Chat/ChatLogComponent/ChatLogComponent";
import ChatSearchInput from "@/app/_components/Chat/ChatComponent/ChatSearchInput";
import styles from "./ChatComponent.module.css";

const ChatComponent = () => {
  const [finalPrompt, setFinalPrompt] = useState("");
  const [searchDisabled, setSearchDisabled] = useState(false);
  const handleSetPrompt = (prompt: string): void => {
    if (!searchDisabled) {
      setSearchDisabled(true);
      setFinalPrompt(prompt);
    }
  }
  return (
    <section className={styles.mainWrapperContainer}>
      <div className={styles.subWrapperContainer}>
        <ChatLogComponent prompt={finalPrompt} enableSearch={() => setSearchDisabled(false)}/>
        <ChatSearchInput searchDisabled={searchDisabled} handleSetPrompt={handleSetPrompt}/>
      </div>
    </section>
  );
}

export default ChatComponent;
