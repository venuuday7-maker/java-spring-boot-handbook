-- Turns ::: {.tip} style fenced divs into labelled callouts that work in HTML and DOCX.
local labels = {
  tip = "Tip", warning = "Warning", interview = "Interview Insight",
  best = "Best Practice", note = "Note", mistake = "Common Mistake",
  analogy = "Real-World Analogy", exercise = "Exercise"
}

function Div(el)
  for _, c in ipairs(el.classes) do
    if labels[c] then
      local head = pandoc.Para({ pandoc.Strong({ pandoc.Str(labels[c]) }) })
      table.insert(el.content, 1, head)
      if FORMAT:match("docx") then
        el.attributes["custom-style"] = "Block Text"
      end
      return el
    end
  end
end
