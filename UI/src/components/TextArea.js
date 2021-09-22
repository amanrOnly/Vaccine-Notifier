import React from "react";

function TextArea({name1,name2}){
    return (
        <div>
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="{name1}"
              label="{name2}"
              type="text"
              id="{name1}"
              autoComplete="{name1}"
            />
        </div>
    );
}

export default TextArea;