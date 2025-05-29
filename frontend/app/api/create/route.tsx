import { NextRequest, NextResponse } from "next/server";

export async function POST(req: NextRequest) {
  try {
    console.log("Received request to create a post");
    const formData = await req.formData();
    const token = formData.get("token")?.toString() || "";
    formData.delete("token"); // Remove token from formData

    // Construct new FormData to forward correctly
    const forwardForm = new FormData();
    formData.forEach((value, key) => {
      if (typeof value === "string") {
        forwardForm.append(key, value);
      } else {
        // Must be a File
        forwardForm.append(key, value, value.name);
      }
    });

    const backendRes = await fetch("http://localhost:8080/posts", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
      },
      body: forwardForm,
    });

    console.log("Backend response status:", backendRes.status);

    if (!backendRes.ok) {
      const errorText = await backendRes.text();
      console.error("Backend error:", backendRes.status, errorText);
      return new NextResponse(errorText, { status: backendRes.status });
    }

    const json = await backendRes.json();
    return NextResponse.json(json);
  } catch (err: any) {
    console.error("Server error:", err);
    return new NextResponse("Server error: " + err.message, { status: 500 });
  }
}
