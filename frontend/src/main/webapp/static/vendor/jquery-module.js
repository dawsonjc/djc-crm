const jquery = globalThis.jQuery;

if (typeof jquery !== "function") {
    throw new Error("The jQuery global was not loaded");
}

export default jquery;