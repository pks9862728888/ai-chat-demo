import {CircularProgress} from "@mui/material";
import styles from "./ThinkingSpinner.module.css";

const ThinkingSpinner = () => {

  return (
    <div className={styles.thinkingDiv}>
      <CircularProgress className={styles.thinkingSpinner} size={25} color="inherit"/>
      <span className={"ml-05r"}>Thinking...</span>
    </div>
  );
}

export default ThinkingSpinner;
